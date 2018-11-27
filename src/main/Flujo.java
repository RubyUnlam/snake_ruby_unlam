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
    private SincronizadorDeSalas sincronizadorDeSalas;
    private Gson gson = new Gson();
    private Jugador jugador;
    private String salaActual = "";

    public Flujo(Socket conexion, SincronizadorDeSalas sincronizadorDeSalas) {
        this.conexion = conexion;
        this.sincronizadorDeSalas = sincronizadorDeSalas;
    }

    @Override
    public void run() {

        Serpiente serpiente = new Serpiente(Color.BLUE);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(conexion.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(conexion.getInputStream());
            RegistroUsuario registroUsuario = null;
            Boolean sesionIniciada = false;
            
            //------------------------REGISTRO-------------------------------//

            Usuario usuario;
            do {
                usuario = gson.fromJson(dataInputStream.readUTF(), Usuario.class);
                if (isNull(usuario.getEmail())) {
                    boolean registroValido = Sesion.iniciarSesion(usuario);
                    if (!registroValido) {
                        registroUsuario = new RegistroUsuario("login invalido", registroValido);
                    } else {
                        registroUsuario = new RegistroUsuario("", registroValido);
                        sesionIniciada = true;
                    }
                } else {
                    registroUsuario = Sesion.registrarUsuario(usuario.getNombreUsuario(), usuario.getContrasenia(), usuario.getEmail());
                }
                dataOutputStream.writeUTF(gson.toJson(registroUsuario));
            } while (!sesionIniciada);
            
            //------------------------REGISTRO-------------------------------//

            //------------------------MENU-------------------------------//
            this.jugador = new Jugador(usuario.getNombreUsuario(), conexion);

            
            String opcion = dataInputStream.readUTF();
            
            while (!"salir".equals(opcion)) { //TODO CONSTANTE
            	if (nonNull(opcion)) {
            		switch (opcion) {
	        			case "ver_salas":
	        				dataOutputStream.writeUTF(gson.toJson(new RespuestaAccionConSala(true, sincronizadorDeSalas.obtenerSalas())));
	        				break;
	        			case "crear_sala":
	        			    Sala salaACrear = gson.fromJson(dataInputStream.readUTF(), Sala.class);
                            RespuestaAccionConSala respuesta = crearSala(salaACrear);
                            dataOutputStream.writeUTF(gson.toJson(respuesta));
	        				break;
	        			case "unirse_a_sala":
                            Sala salaAUnirse = gson.fromJson(dataInputStream.readUTF(), Sala.class);
                            RespuestaAccionConSala respuestaAccionConSala = unirseASala(salaAUnirse);
                            dataOutputStream.writeUTF(gson.toJson(respuestaAccionConSala));
                            break;
                        case "salir_de_sala":
                            salirDeSalaActual(salaActual);
                            salaActual = "";
                            dataOutputStream.writeUTF("");
                            break;
                        case "jugar":
                            if (!salaActual.isEmpty()) {
                                iniciarJuego(salaActual);
                            }
                            break;
            		}
            	}
        		opcion = dataInputStream.readUTF();
            }

            //------------------------MENU-------------------------------//


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void iniciarJuego(String nombreDeSala) {
        for (Sala sala : this.sincronizadorDeSalas.obtenerSalas()) {
            if (sala.getNombreSala().equals(nombreDeSala) && sala.getNombreCreador().equals(jugador.getNombre())) {
                Juego.iniciar(sala, null);
            }
        }
    }

    private void salirDeSalaActual(String nombreDeSala) {
        for (Sala sala : this.sincronizadorDeSalas.obtenerSalas()) {
            if (sala.getNombreSala().equals(nombreDeSala)) {
                sala.removerJugador(jugador);
            }
        }
    }

    /**
     * Dada una sala a unirse busca esta entre las salas existentes. Si la encuentra y la
     * contraseña concuerda une al jugador y responde de manera correcta. En otro caso
     * devuelve el mensaje de error correspondiente.
     * @param salaAUnirse
     * @return
     */
    private RespuestaAccionConSala unirseASala(Sala salaAUnirse) {
        for (Sala sala : this.sincronizadorDeSalas.obtenerSalas()) {
            if (sala.getNombreSala().equals(salaAUnirse.getNombreSala())) {
                if (isNull(sala.getContrasenia()) || sala.getContrasenia().equals(salaAUnirse.getContrasenia())) {
                    sala.agregarJugador(jugador);
                    this.salaActual = sala.getNombreSala();
                    return new RespuestaAccionConSala(true, this.sincronizadorDeSalas.obtenerSalas());
                } else {
                    return new RespuestaAccionConSala(false, "Contraseña invalida");
                }
            }
        }
        return new RespuestaAccionConSala(false, "Sala no encontrada");
    }

    /**
     * Dada una sala, devuelve si se puede crear
     * @param salaACrear
     * @return
     */
    private RespuestaAccionConSala crearSala(Sala salaACrear) {
        if (salaACrear.getNombreSala().isEmpty()){
            return new RespuestaAccionConSala(false, "El nombre no puede estar vacío");
        }

        if(!cantidadJugadoresValida(salaACrear)){
            return new RespuestaAccionConSala(false, "La cantidad total de jugadores debe ser a lo sumo 4");
        }

        if(nombreUsado(salaACrear.getNombreSala())){
            return new RespuestaAccionConSala(false, "Ya existe una sala con ese nombre");
        }
        salaACrear.agregarJugador(jugador);
        salaACrear.crearCuentaRegresiva();
        this.salaActual = salaACrear.getNombreSala();
        this.sincronizadorDeSalas.agregarSala(salaACrear);
        return new RespuestaAccionConSala(true, this.sincronizadorDeSalas.obtenerSalas());
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
        for(Sala sala : this.sincronizadorDeSalas.obtenerSalas()){
            if(sala.getNombreSala().equals(nombreSala)) {
                return true;
            }
        }
        return false;
    }

}
