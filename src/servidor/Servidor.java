package servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import main.Serpiente;

public class Servidor extends Thread {
	
	private ChatEscrituraServidor chatEscritura;
	private List<Serpiente> serpientes;
	private int puerto;
	private ServerSocket serverSocket;

    public Servidor(int puerto, List<Serpiente> serpientes) {
    	this.puerto = puerto;
    	this.serpientes = serpientes;
        try {
			this.serverSocket = new ServerSocket(puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        this.chatEscritura = new ChatEscrituraServidor();
    }
    
    @Override
    public void run() {
        try {
            while (true) {

            Socket jugador = serverSocket.accept();

            System.out.println("Alguien ha ingresado");

            this.chatEscritura.agregarJugador(jugador);
            
            Serpiente serpiente = new Serpiente();
            
            serpientes.add(serpiente);

            ChatLecturaServidor chatLectura = new ChatLecturaServidor(jugador, serpiente);
            
            chatLectura.start();            

            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				this.serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public ChatEscrituraServidor getEscribir() {
    	return chatEscritura;
    }
}
