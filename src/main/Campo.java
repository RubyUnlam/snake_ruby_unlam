package main;

import static main.Juego.initTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.Timer;


public class Campo implements KeyListener, ActionListener, Observado {
	
	private Timer timer;
	private int delay = 100;
	
	private List<Serpiente> serpientes;
	private List<Serpiente> serpientesIA;
	private Queue<Comestible> comestibles;
	
	private int keyEventUP = KeyEvent.VK_UP;
	private int keyEventDOWN = KeyEvent.VK_DOWN;
	private int keyEventRIGTH = KeyEvent.VK_RIGHT;
	private int keyEventLEFT = KeyEvent.VK_LEFT;

	private List<Observador> observadores = new ArrayList<>();
	
	Campo(List<Serpiente> jugadores, List<Serpiente> serpientesIA) {
		this.serpientes = jugadores;
		this.serpientesIA = serpientesIA;
		this.comestibles = new ConcurrentLinkedQueue<Comestible>();
		timer = new Timer(delay, this);
		timer.start();
	}

	public List<Ubicacion> notificarUbicacionesSerpientes() {
	    List<Ubicacion> ubicacionesActuales = new ArrayList<>();
	    for(Serpiente serpientes : serpientes){
	        ubicacionesActuales.addAll(serpientes.getUbicaciones());
        }
        for(Serpiente serpientesIA : serpientesIA){
            ubicacionesActuales.addAll(serpientesIA.getUbicaciones());
        }

        return ubicacionesActuales;
    }

    public List<Ubicacion> notificarUbicacionesComestibles() {
	    List<Ubicacion> ubicacionesActuales = new ArrayList<>();
        for (Comestible comestible : comestibles) {
            ubicacionesActuales.add(comestible.getUbicacion());
        }
        return ubicacionesActuales;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (comestibles.isEmpty()) {
			comestibles.add(new Manzana());
		}
		
        for (Serpiente jugador : serpientes) {
            jugador.moverse();
        }

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
        }
        
        for (Observador observador : observadores) {
        	observador.dibujar(this);
        }
        
        if(serpientes.isEmpty()) {
        	// TODO: Cerrar ventana del juego y volver al menu, previamente mostrar mensaje de victoria/derrota/puntajes
        }
    }

	
	@Override
	public void keyPressed(KeyEvent e) { //TODO VER COMO FUNCIONARIA ESTO EN MULTIJUGADOR
		int teclaPresionada = e.getKeyCode();
		if (teclaPresionada == keyEventUP) {
			serpientes.get(0).mirarArriba();
		} else if (teclaPresionada == keyEventDOWN) {
			serpientes.get(0).mirarAbajo();
		} else if (teclaPresionada == keyEventRIGTH) {
            initTime = System.currentTimeMillis();
			serpientes.get(0).mirarDerecha();
		} else if (teclaPresionada == keyEventLEFT) {
			serpientes.get(0).mirarIzquierda();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	/**
	 * Metodo para agregar un observador a la lista.
	 * Cada jugador deberia ser un observador
	 */
	@Override
	public void agregarObservador(Observador observador) {
		observadores.add(observador);
	}

}
