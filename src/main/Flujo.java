package main;

import com.google.gson.Gson;
import servidor.ManejadorMovimiento;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.awt.Color;

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
}
