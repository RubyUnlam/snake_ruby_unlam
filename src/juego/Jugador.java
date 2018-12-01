package juego;

import main.Sala;
import org.apache.log4j.Logger;
import servidor.ManejadorES;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Jugador {

    private String nombre;
    private Color color = Color.BLUE;
    private transient ManejadorES manejadorES;
    private transient boolean estaListo;
    private transient CountDownLatch escuchandoTeclas;
    private transient final Logger logger = Logger.getLogger(Jugador.class);

    public Jugador(String nombre, ManejadorES manejadorES) {
        this.nombre = nombre;
        this.manejadorES = manejadorES;
    }

    public ManejadorES getManejador() {
        return manejadorES;
    }

    public Color getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void notificarActualizacionDeSala(Sala sala) {
        try {
            manejadorES.enviar(sala);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean getEstaListo() {
        return estaListo;
    }

    public void setEstaListo(boolean estaListo) {
        this.estaListo = estaListo;
    }

    public void cerrarActualizacionDeSala() {
        try {
            manejadorES.enviarString("");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public CountDownLatch obtenerEscuchandoTeclas() {
        return escuchandoTeclas;
    }

    public void setEscuchandoTeclas(CountDownLatch escuchandoTeclas) {
        this.escuchandoTeclas = escuchandoTeclas;
    }
}
