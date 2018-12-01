package juego;

import juego.serpiente.Serpiente;
import juego.serpiente.SerpienteIA;
import main.Sala;
import org.apache.log4j.Logger;
import servidor.ManejadorMovimiento;
import servidor.ManejadorVisual;
import utilidades.Constantes;
import utilidades.NombreIA;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static utilidades.Constantes.CARNICERIA;

public class Juego {

    private static Logger logger = Logger.getLogger(Juego.class);

    public static void iniciar(Sala sala) {

        List<Serpiente> serpientes = new ArrayList<>();
        List<SerpienteIA> serpientesIA = new ArrayList<>();
        ManejadorVisual manejadorVisual = new ManejadorVisual();

        CountDownLatch finDelJuego = new CountDownLatch(1);

        if (CARNICERIA.equals(sala.getModoDeJuego())) {
            for (Jugador jugador : sala.getJugadores()) {
                manejadorVisual.agregarJugador(jugador.getManejador(), jugador.getNombre());
                new ManejadorMovimiento(jugador.getManejador(), new Serpiente(jugador.getColor(), jugador.getNombre()), jugador.obtenerEscuchandoTeclas()).start();
            }
        } else {
            for (Jugador jugador : sala.getJugadores()) {
                Serpiente serpiente = new Serpiente(jugador.getColor(), jugador.getNombre());
                serpientes.add(serpiente);
                manejadorVisual.agregarJugador(jugador.getManejador(), jugador.getNombre());
                new ManejadorMovimiento(jugador.getManejador(), serpiente, jugador.obtenerEscuchandoTeclas()).start();
            }
        }

        List<String> nombresIAUsados = new ArrayList<>();
        int randomNameIndex;
        String nombreActual;
        Color colorActual;

        for (int i = 0; i < sala.getCantidadIA(); i++) {
            do {
                randomNameIndex = (int) (Math.random() * NombreIA.values().length);
                nombreActual = NombreIA.values()[randomNameIndex].nombre();
                colorActual = NombreIA.values()[randomNameIndex].color();
            } while (nombresIAUsados.contains(nombreActual));
            nombresIAUsados.add(nombreActual);
            serpientesIA.add(new SerpienteIA(sala.getDificultadIA(), colorActual,
                    nombreActual + Constantes.NOMBRE_IA));
        }

        Campo campo = new Campo(serpientes, serpientesIA, finDelJuego, sala.getTiempo(), sala.getPuntajeAAlcanzar(), sala.getModoDeJuego());
        campo.agregarObservador(manejadorVisual);

        campo.comenzarJuego();

        try {
            finDelJuego.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

}
