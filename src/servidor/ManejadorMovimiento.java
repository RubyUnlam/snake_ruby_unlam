package servidor;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import main.Serpiente;

public class ManejadorMovimiento extends Thread {

    public static final String FINALIZAR = "finalizar";
    private ManejadorES manejadorES;
    private Serpiente serpiente;
    private CountDownLatch countDownLatch;

    public ManejadorMovimiento(ManejadorES manejadorES, Serpiente serpiente, CountDownLatch countDownLatch) {
        this.manejadorES = manejadorES;
        this.serpiente = serpiente;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            boolean finDelJuego = false;
            while (!finDelJuego) {
                String mandameMecha = manejadorES.escuchar(String.class);
                if (!FINALIZAR.equals(mandameMecha)) {
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
