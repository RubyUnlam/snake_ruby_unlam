package main;

import java.util.ArrayList;
import java.util.List;

public class SincronizadorDeSalas {

    private List<Sala> salas = new ArrayList<>();

    public synchronized List<Sala> obtenerSalas() {
        return salas;
    }

    public synchronized void agregarSala(Sala sala) {
        salas.add(sala);
    }

}
