package juego.serpiente;

import org.apache.log4j.Logger;

public class GestorDeEstado extends Thread  {

    private Serpiente serpiente;
    private Estado estadoNuevo;
    private int delay;
    private final Logger logger = Logger.getLogger(GestorDeEstado.class);

    public GestorDeEstado(Serpiente serpiente, Estado estadoNuevo, int delay) {
        this.serpiente = serpiente;
        this.estadoNuevo = estadoNuevo;
        this.delay = delay;
    }

    @Override
    public void run() {
        Estado anterior = serpiente.getEstado();
        serpiente.setEstado(estadoNuevo);
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        serpiente.setEstado(anterior);
    }

}
