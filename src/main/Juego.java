package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Juego {

    public static void iniciar(Sala sala) {


        List<Serpiente> serpientes = new ArrayList<>();

        for (int i = 0; i < sala.getCantidadJugadores(); i++) {
            serpientes.add(new Serpiente(Color.BLUE));
        }

        List<SerpienteIA> serpientesIA = new ArrayList<>();
        for (int i = 0; i < sala.getCantidadIA(); i++) {
            serpientesIA.add(new SerpienteIA(sala.getDificultadIA(), Color.BLACK));
        }


        Campo campo = new Campo(serpientes, serpientesIA);

    }

}
