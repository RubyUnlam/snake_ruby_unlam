package servidor;

import java.util.ArrayList;
import java.util.List;

public class SincronizadorUsuariosLoggeados {
    private List<String> jugadoresLoggeados = new ArrayList<String>();

    public synchronized boolean estaLoggeado(String nombre) {
        return jugadoresLoggeados.contains(nombre);
    }

    public void insertarJugador(String nombre) {
        jugadoresLoggeados.add(nombre);
    }

    public void eliminarJugador(String nombre) {
        jugadoresLoggeados.remove(nombre);
    }
}
