package juego.serpiente;

public class GestorDeEstado extends Thread {

    private Serpiente serpiente;
    private Estado estadoNuevo;
    private int delay;

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
            e.printStackTrace();
        }
        serpiente.setEstado(anterior);
    }


}
