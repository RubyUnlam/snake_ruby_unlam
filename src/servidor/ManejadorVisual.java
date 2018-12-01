package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import main.ActualizacionDelJuego;
import main.Flujo;
import main.Observador;
import org.apache.log4j.Logger;

public class ManejadorVisual implements Observador {

    private Map<String, ManejadorES> manejadores = new HashMap<>();
    private final Logger logger = Logger.getLogger(ManejadorVisual.class);

    public void dibujar(ActualizacionDelJuego actualizacion) {
        try {
            for (ManejadorES manejadorES : manejadores.values()) {
                manejadorES.enviar(actualizacion);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void agregarJugador(ManejadorES manejadorDelJugador, String jugador) {
        manejadores.put(jugador, manejadorDelJugador);
    }

    @Override
    public void removerJugador(String jugador) {
        if (manejadores.containsKey(jugador)) {
            ManejadorES manejadorES = manejadores.get(jugador);
            try {
                manejadorES.enviar(new ActualizacionDelJuego(true));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            manejadores.remove(jugador);
        }
    }

}
