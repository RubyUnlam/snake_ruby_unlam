package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import main.ActualizacionDelJuego;
import main.Observador;

public class ManejadorVisual implements Observador {

    private Map<String, ManejadorES> manejadores = new HashMap<>();

    public void dibujar(ActualizacionDelJuego actualizacion) {
        try {
            for (ManejadorES manejadorES : manejadores.values()) {
                manejadorES.enviar(actualizacion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregarJugador(ManejadorES manejadorDelJugador, String jugador) {
        manejadores.put(jugador, manejadorDelJugador);
    }

    @Override
    public void removerJugador(String jugador) {
        if (manejadores.containsKey(jugador)) {
            System.out.println("Removiendo jugador");
            ManejadorES manejadorES = manejadores.get(jugador);
            try {
                manejadorES.enviar(new ActualizacionDelJuego(true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            manejadores.remove(jugador);
        }
    }

}
