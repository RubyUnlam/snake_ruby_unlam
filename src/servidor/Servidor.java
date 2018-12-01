package servidor;
import main.Flujo;
import main.SincronizadorDeSalas;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor extends Thread {
	
	private ServerSocket serverSocket;
	private SincronizadorDeSalas sincronizadorDeSalas = new SincronizadorDeSalas();
	private SincronizadorUsuariosLoggeados sincrozadorDeUsuariosLoggeados = new SincronizadorUsuariosLoggeados();

    public Servidor(int puerto) {
        try {
			this.serverSocket = new ServerSocket(puerto);
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    @Override
    public void run() {
        try {
            while (true) {
            new Flujo(serverSocket.accept(), sincronizadorDeSalas, sincrozadorDeUsuariosLoggeados).start();
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
