package servidor;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import juego.serpiente.Serpiente;
import org.apache.log4j.Logger;

public class ManejadorMovimiento extends Thread {

    public static final String FINALIZAR = "finalizar";
    private ManejadorES manejadorES;
    private Serpiente serpiente;
    private CountDownLatch countDownLatch;
    private final Logger logger = Logger.getLogger(ManejadorMovimiento.class);

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
                    if (!serpiente.estaMuerto()) {
                        serpiente.mirar(mandameMecha);
                    }
                } else {
                    serpiente.setSalir(true);
                    finDelJuego = true;
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        countDownLatch.countDown();
    }
}
