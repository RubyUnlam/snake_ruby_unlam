package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import javax.swing.Timer;


public class Campo implements ActionListener, Observado {

	private Timer timer;
	private int delay = 100;
	private CountDownLatch finDelJuego;
	
	private List<Serpiente> serpientes;
	private List<SerpienteIA> serpientesIA;
	private Queue<Comestible> comestibles;
	private int ciclos;
	private ActualizacionDelJuego actualizacionDelJuego;

	private Observador observador;
	
	Campo(List<Serpiente> jugadores, List<SerpienteIA> serpientesIA, CountDownLatch finDelJuego) {
		this.serpientes = jugadores;
		this.serpientesIA = serpientesIA;
		this.comestibles = new ConcurrentLinkedQueue<Comestible>();
        this.finDelJuego = finDelJuego;
		timer = new Timer(delay, this);
	}
	
	public void comenzarJuego() {
		timer.start();
	}

	public void terminarJuego() {
	    timer.stop();
    }


	public void notificarDibujables(ActualizacionDelJuego actualizacion) {
		observador.dibujar(actualizacion);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (comestibles.isEmpty()) {
			comestibles.add(new Manzana());
		}
		
        for (Serpiente jugador : serpientes) {
            jugador.moverse();
        }
        for (SerpienteIA jugadorIA : serpientesIA) {
        	jugadorIA.cambiarMirada(comestibles.peek());
        	jugadorIA.moverse();
        }

		chequearColisiones(serpientes);
		chequearColisiones(serpientesIA);

        prepararActualizacionDelJuego();

        ciclos++;
        notificarDibujables(actualizacionDelJuego);
    }

    private void prepararActualizacionDelJuego() {
        //TODO CONDICION DE FIN DE JUEGO
        List<Dibujable> dibujables = prepararDibujables();
        if (ciclos > 20) {
            terminarJuego();
            actualizacionDelJuego = new ActualizacionDelJuego(true, dibujables, "Peter"); //TODO LEVANTAR NOMBRE DEL GANADOR
            this.finDelJuego.countDown();
        } else {
            actualizacionDelJuego = new ActualizacionDelJuego(dibujables); //TODO LEVANTAR NOMBRE DEL GANADOR
        }
    }

    /**
     * Genera un dibujable por cada serpiente y comestible en el campo
     * @return una lista de dibujables
     */
    private List<Dibujable> prepararDibujables() {
        List<Dibujable> dibujables = new ArrayList<>();
        for(Serpiente serpientes : serpientes){
            dibujables.add(new Dibujable(serpientes));
        }

        for(Serpiente serpientesIA : serpientesIA){
            dibujables.add(new Dibujable(serpientesIA));
        }

        for (Comestible comestible : comestibles) {
            dibujables.add(new Dibujable(comestible));
        }
        return dibujables;
    }

    private void chequearColisiones(List<? extends Serpiente> serpientes) {
		for (Serpiente jugador : serpientes) {
			for (Comestible comestible : comestibles) {
				jugador.checkearColision(comestible);
				if (comestible.fueComida()) {
					comestibles.remove(comestible);
				}
			}

			for (Serpiente jugador2 : serpientes) {
				jugador.checkearColision(jugador2);
			}
			for (Serpiente jugadorIA : serpientesIA) {
				jugador.checkearColision(jugadorIA);
			}
		}
	}

	/**
	 * Metodo para agregar un observador a la lista.
	 * Cada jugador deberia ser un observador
	 */
	@Override
	public void agregarObservador(Observador observador) {
		this.observador = observador;
	}

    public ActualizacionDelJuego obtenerResultadoDelJuego() {
        return actualizacionDelJuego;
    }
}
