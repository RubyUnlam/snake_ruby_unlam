package main;

import servidor.ManejadorMovimiento;
import servidor.ManejadorVisual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Juego {

    public static void iniciar(Sala sala, CountDownLatch partidoTerminado) { //TODO VER SI LO SEPARAMOS EN METODOS

        List<Serpiente> serpientes = new ArrayList<>();
        List<SerpienteIA> serpientesIA = new ArrayList<>();

        ManejadorVisual manejadorVisual = new ManejadorVisual();

        CountDownLatch finDelJuego = new CountDownLatch(1);

        Campo campo = new Campo(serpientes, serpientesIA, finDelJuego, sala.getTiempo(), sala.getPuntajeAAlcanzar(), sala.getModoDeJuego());
        campo.agregarObservador(manejadorVisual);

        for (Jugador jugador : sala.getJugadores()) {
            Serpiente serpiente = new Serpiente(jugador.getColor(), jugador.getNombre());
            serpientes.add(serpiente);
            manejadorVisual.agregarJugador(jugador.getManejador(), jugador.getNombre());
            new ManejadorMovimiento(jugador.getManejador(), serpiente, jugador.obtenerEscuchandoTeclas()).start();
        }

        for (Integer i = 0; i < sala.getCantidadIA(); i++) {
            serpientesIA.add(new SerpienteIA(sala.getDificultadIA(), Color.BLACK, "IA " + i.toString()));
        }

        campo.comenzarJuego();

        try {
            finDelJuego.await();
            partidoTerminado.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
