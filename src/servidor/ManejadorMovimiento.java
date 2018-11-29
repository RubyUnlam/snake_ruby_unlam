package servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import main.Serpiente;

public class ManejadorMovimiento extends Thread {

    private Socket socket;
    private Serpiente serpiente;
    private CountDownLatch countDownLatch;

    public ManejadorMovimiento(Socket socket, Serpiente serpiente, CountDownLatch countDownLatch) {
        this.socket = socket;
        this.serpiente = serpiente;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            boolean finDelJuego = false;
            while (!finDelJuego) {
                String mandameMecha = entrada.readUTF();
                System.out.println(mandameMecha);
                if (!"finalizar".equals(mandameMecha)) {
                    serpiente.mirar(mandameMecha);
                } else {
                    finDelJuego = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
    }
}
