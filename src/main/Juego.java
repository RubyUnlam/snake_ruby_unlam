package main;

import servidor.ManejadorMovimiento;
import servidor.ManejadorVisual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Juego {

    public static void iniciar(Sala sala, CountDownLatch cuentaRegresiva ) {

        List<Serpiente> serpientes = new ArrayList<>();
        List<SerpienteIA> serpientesIA = new ArrayList<>();
        ManejadorVisual manejadorVisual = new ManejadorVisual();

        Campo campo = new Campo(serpientes, serpientesIA, cuentaRegresiva);
        campo.agregarObservador(manejadorVisual);

        for (Jugador jugador : sala.getJugadores()) {
            Serpiente serpiente = new Serpiente(jugador.getColor());
            serpientes.add(serpiente);
            manejadorVisual.agregarJugador(jugador.getConexion());
            new ManejadorMovimiento(jugador.getConexion(), serpiente).start();
        }

        for (int i = 0; i < sala.getCantidadIA(); i++) {
            serpientesIA.add(new SerpienteIA(sala.getDificultadIA(), Color.BLACK));
        }

        campo.comenzarJuego();

        try {
            System.out.println("Esperando");
            cuentaRegresiva.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
