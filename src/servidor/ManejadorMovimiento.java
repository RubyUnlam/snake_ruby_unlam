package servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

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
            boolean finDelJuego = false;
            while (!finDelJuego) {
                String mandameMecha = entrada.readUTF();
                if (!"finalizar".equals(mandameMecha)) {
                    serpiente.mirar(mandameMecha);
                } else {
                    System.out.println("Salgo del thread de movimientos");
                    finDelJuego = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
