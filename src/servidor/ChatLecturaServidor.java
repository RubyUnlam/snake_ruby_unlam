package servidor;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import main.Serpiente;

public class ChatLecturaServidor extends Thread {

    private Socket socket;
    private Serpiente serpiente;

    ChatLecturaServidor(Socket socket, Serpiente serpiente) {
        this.socket = socket;
        this.serpiente = serpiente;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            while (true) {
                String mandanmeMecha = entrada.readUTF();
                serpiente.mirar(mandanmeMecha);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
