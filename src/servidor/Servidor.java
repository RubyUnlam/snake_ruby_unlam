package servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.String;
import main.Flujo;
import main.Sala;
import main.Serpiente;
import main.SincronizadorDeSalas;

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
