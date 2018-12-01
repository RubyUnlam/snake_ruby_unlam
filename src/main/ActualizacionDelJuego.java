package main;

import java.util.List;

public class ActualizacionDelJuego {

    private boolean terminado;
    List<Dibujable> dibujables;
    private String ganador;
    private boolean salir;

    public ActualizacionDelJuego(boolean terminado, List<Dibujable> dibujables, String ganador) {
        this.dibujables = dibujables;
        this.terminado = terminado;
        this.ganador = ganador;
    }

    public ActualizacionDelJuego(List<Dibujable> dibujables) {
        this.dibujables = dibujables;
        this.terminado = false;
        this.ganador = "";
    }

    public ActualizacionDelJuego(boolean salir){
        this.salir = salir;
    }
}
