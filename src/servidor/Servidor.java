package servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

import main.Flujo;
import main.Sala;
import main.Serpiente;
import main.SincronizadorDeSalas;

public class Servidor extends Thread {
	
	private ServerSocket serverSocket;
	private SincronizadorDeSalas sincronizadorDeSalas = new SincronizadorDeSalas();

    public Servidor(int puerto) {
        try {
			this.serverSocket = new ServerSocket(puerto);

			System.out.println("Creado el server");
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    @Override
    public void run() {
        try {
            while (true) {

            System.out.println("Esperando jugadores");

            Socket jugador = serverSocket.accept();

            System.out.println("Alguien ha ingresado");

            new Flujo(jugador, sincronizadorDeSalas).start();

            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				this.serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}
