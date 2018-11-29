package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.ActualizacionDelJuego;
import main.Observador;

public class ManejadorVisual implements Observador {

    private List<ManejadorES> manejadores = new ArrayList<>();
    private Gson gson = new Gson();

    public void dibujar(ActualizacionDelJuego actualizacion) {
        try {
            for (ManejadorES manejadorES : manejadores) {
                manejadorES.enviar(gson.toJson(actualizacion));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregarJugador(ManejadorES manejadorDelJugador) {
        manejadores.add(manejadorDelJugador);
    }
}
