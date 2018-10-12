package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import static main.Juego.initTime;


public class Campo extends Observable implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 100;
	
	private List<Serpiente> serpientes;
	private List<Serpiente> serpientesIA;
	private Queue<Comestible> comestibles;
	
	private int keyEventUP = KeyEvent.VK_UP;
	private int keyEventDOWN = KeyEvent.VK_DOWN;
	private int keyEventRIGTH = KeyEvent.VK_RIGHT;
	private int keyEventLEFT = KeyEvent.VK_LEFT;

	public Observer observer;
	
	Campo(List<Serpiente> jugadores, List<Serpiente> serpientesIA) {
		this.serpientes = jugadores;
		this.serpientesIA = serpientesIA;
		this.comestibles = new ConcurrentLinkedQueue();
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
		//Spawn de manzana, validacion para no spawnear sobre serpiente
		Comestible manzana; // TODO: extraer a un metodo.
		boolean spawneaEncimaDeAlgo = false;
		if (comestibles.isEmpty()) {
			do {
				manzana = new Manzana();
				for (Serpiente serpiente : serpientes) {
					if ( serpiente.getUbicaciones().contains(manzana.getUbicacion()) ) {
						spawneaEncimaDeAlgo = true;
					}
				}
			} while (spawneaEncimaDeAlgo);
			comestibles.add(manzana);
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
        observer.update(this, "dibuja");
    }

    @Override
    public void addObserver(Observer observer){
        this.observer = observer;
        observer.update(this, "campo");
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

}
