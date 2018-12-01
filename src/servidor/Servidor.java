package servidor;
import main.Flujo;
import main.SincronizadorDeSalas;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor extends Thread {

	private ServerSocket serverSocket;
	private SincronizadorDeSalas sincronizadorDeSalas = new SincronizadorDeSalas();
	private SincronizadorUsuariosLoggeados sincrozadorDeUsuariosLoggeados = new SincronizadorUsuariosLoggeados();
	private final Logger logger = Logger.getLogger(Servidor.class);

    public Servidor(int puerto) {
        try {
			this.serverSocket = new ServerSocket(puerto);
		} catch (IOException e) {
            logger.error(e.getMessage());
		}
    }

    @Override
    public void run() {
        try {
            while (true) {
                new Flujo(serverSocket.accept(), sincronizadorDeSalas, sincrozadorDeUsuariosLoggeados).start();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
        	try {
				this.serverSocket.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
        }
    }
}
