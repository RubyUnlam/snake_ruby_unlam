package main;

import com.google.gson.Gson;
import servidor.ManejadorMovimiento;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Flujo extends Thread { //TODO PENSAR EL NOMBRE PARA ESTO

    private Socket conexion;
    private List<Sala> salas;
    private Gson gson = new Gson();

    public Flujo(Socket conexion, List<Sala> salas) {
        this.conexion = conexion;
        this.salas = salas;
    }

    @Override
    public void run() {

        Serpiente serpiente = new Serpiente(Color.BLUE);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(conexion.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(conexion.getInputStream());
            RegistroUsuario registroUsuario = null;
            
            //------------------------REGISTRO-------------------------------//

            do {
                Usuario usuario = gson.fromJson(dataInputStream.readUTF(), Usuario.class);
                if (isNull(usuario.getEmail())) {
                    boolean registroValido = Sesion.iniciarSesion(usuario);
                    if (!registroValido) {
                        registroUsuario = new RegistroUsuario("login invalido", registroValido);
                    } else {
                        registroUsuario = new RegistroUsuario("", registroValido);
                    }
                } else {
                    registroUsuario = Sesion.registrarUsuario(usuario.getNombreUsuario(), usuario.getContrasenia(), usuario.getEmail());
                }
                dataOutputStream.writeUTF(gson.toJson(registroUsuario));
            } while (!registroUsuario.esRegistroEfectivo());
            
            //------------------------REGISTRO-------------------------------//
            
            
            String opcion = dataInputStream.readUTF();
            
            while (!"jugar".equals(opcion)) { //TODO CONSTANTE
            	if (nonNull(opcion)) {
            		switch (opcion) {
	        			case "ver_salas":
	        				System.out.println("enviando salas");
	        				dataOutputStream.writeUTF(gson.toJson(salas));
	        				break;
	        			case "crear_sala":
	        			    Sala salaACrear = gson.fromJson(dataInputStream.readUTF(), Sala.class);
                            RespuestaCreacionSala respuesta = crearSala(salaACrear);
                            dataOutputStream.writeUTF(gson.toJson(respuesta));
	        				break;
	        			case "unirse_a_sala":
	        				break;
            		}
            	}
            	System.out.println("Leyendo");
        		opcion = dataInputStream.readUTF();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        ManejadorMovimiento chatLectura = new ManejadorMovimiento(conexion, serpiente);
        chatLectura.start();
    }

    /**
     * Dada una sala, devuelve si se puede crear
     * @param salaACrear
     * @return
     */

    private RespuestaCreacionSala crearSala(Sala salaACrear) {
        if (camposCreacionSalaVacios(salaACrear)){
            String mensaje = "Rellene todos los campos";
            return new RespuestaCreacionSala(false, mensaje);
        }

        if(!cantidadJugadoresValida(salaACrear)){
            String mensaje = "La cantidad total de jugadores debe ser a lo sumo 4";
            return new RespuestaCreacionSala(false, mensaje);
        }

        if(nombreUsado(salaACrear.getNombreSala())){
            String mensaje = "Ya existe una sala con ese nombre";
            return new RespuestaCreacionSala(false, mensaje);
        }
        this.salas.add(salaACrear);
        return new RespuestaCreacionSala(true, this.salas);
    }

    /**
     * Devuelve false si la cantidad de jugadores no es válida
     * @param salaACrear
     * @return
     */
    private boolean cantidadJugadoresValida(Sala salaACrear) {
       return salaACrear.getCantidadJugadores() + salaACrear.getCantidadIA() <= 4;
    }

    /**
     * Devuelve false si el nombre de la sala ya existe en la lista
     * @param nombreSala
     * @return
     */
    private boolean nombreUsado(String nombreSala) {
        for(Sala sala : this.salas){
            if(sala.getNombreSala().equals(nombreSala)) { return true;}
        }
        return false;
    }

    /**
     * Devuelve true si los campos de la sala se encuentran vacíos
     * @param salaACrear
     * @return
     */
    private boolean camposCreacionSalaVacios(Sala salaACrear) {
            return salaACrear.getContrasenia().isEmpty() || salaACrear.getNombreSala().isEmpty();
    }
}
