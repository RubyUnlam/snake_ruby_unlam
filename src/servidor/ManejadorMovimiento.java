package servidor;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import main.Serpiente;

public class ManejadorMovimiento extends Thread {

    private Socket socket;
    private Serpiente serpiente;

    public ManejadorMovimiento(Socket socket, Serpiente serpiente) {
        this.socket = socket;
        this.serpiente = serpiente;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            while (true) {
                System.out.println("Escuchando movimientos");
                String mandanmeMecha = entrada.readUTF();
                System.out.println(mandanmeMecha);
                serpiente.mirar(mandanmeMecha);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
