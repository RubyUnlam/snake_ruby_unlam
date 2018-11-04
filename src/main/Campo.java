package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
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

	private Observador observador;
	
	Campo(List<Serpiente> jugadores, List<Serpiente> serpientesIA) {
		this.serpientes = jugadores;
		this.serpientesIA = serpientesIA;
		this.comestibles = new ConcurrentLinkedQueue<Comestible>();
		timer = new Timer(delay, this);
	}
	
	public void comenzarJuego() {
		timer.start();
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
        
    	notificarUbicacionesSerpientes();
    }
	
	public void notificarUbicacionesSerpientes() {
	    List<List<Ubicacion>> ubicacionesActuales = new ArrayList<>();
	    for(Serpiente serpientes : serpientes){
	    	ubicacionesActuales.add(serpientes.getUbicaciones());
        }
        for(Serpiente serpientesIA : serpientesIA){
            ubicacionesActuales.add(serpientesIA.getUbicaciones());
        }
        
        List<Ubicacion> ubicacionesActualesComestibles = new ArrayList<>();
        for (Comestible comestible : comestibles) {
        	ubicacionesActualesComestibles.add(comestible.getUbicacion());
        }

        observador.dibujar(new UbicacionesDTO(ubicacionesActuales, ubicacionesActualesComestibles));
    }
	
	@Override
	public void keyPressed(KeyEvent e) { //TODO VER COMO FUNCIONARIA ESTO EN MULTIJUGADOR
		int teclaPresionada = e.getKeyCode();
		if (teclaPresionada == keyEventUP) {
			serpientes.get(0).mirar(Direccion.ARRIBA.name());
		} else if (teclaPresionada == keyEventDOWN) {
			serpientes.get(0).mirar(Direccion.ABAJO.name());
		} else if (teclaPresionada == keyEventRIGTH) {
            serpientes.get(0).mirar(Direccion.DERECHA.name());
		} else if (teclaPresionada == keyEventLEFT) {
			serpientes.get(0).mirar(Direccion.IZQUIERDA.name());
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
		this.observador = observador;
	}

}
