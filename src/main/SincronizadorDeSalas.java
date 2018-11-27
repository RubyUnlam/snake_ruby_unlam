package main;

import java.util.ArrayList;
import java.util.List;

public class SincronizadorDeSalas {

    private List<Sala> salas = new ArrayList<>();

    public synchronized List<Sala> obtenerSalas() {

        return salas; //TODO HACER UNA COPIA PARA EVITAR CONCURRENCIA
    }

    public synchronized void agregarSala(Sala sala) {
        salas.add(sala);
    }

    public synchronized void eliminarSala(Sala sala) {
        salas.remove(sala);
    }

}
