package main;

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
	private List<SerpienteIA> serpientesIA;
	private Queue<Comestible> comestibles;
	
	private int keyEventUP = KeyEvent.VK_UP;
	private int keyEventDOWN = KeyEvent.VK_DOWN;
	private int keyEventRIGTH = KeyEvent.VK_RIGHT;
	private int keyEventLEFT = KeyEvent.VK_LEFT;

	private List<Observador> observadores = new ArrayList<>();
	
	Campo(List<Serpiente> jugadores, List<SerpienteIA> serpientesIA) {
		this.serpientes = jugadores;
		this.serpientesIA = serpientesIA;
		this.comestibles = new ConcurrentLinkedQueue<Comestible>();
		timer = new Timer(delay, this);
		timer.start();
	}

	public List<Dibujable> notificarDibujables() {
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
        // LA serpiente jugador muere bien
        for (Serpiente jugadorIA : serpientesIA) {
        	for (Comestible comestible : comestibles) {
                jugadorIA.checkearColision(comestible);
                if (comestible.fueComida()) {
                    comestibles.remove(comestible);
                }
            }
        	 for (Serpiente jugador2 : serpientes) {
                 jugadorIA.checkearColision(jugador2);
             }
             for (Serpiente jugadorIA2 : serpientesIA) {
             	jugadorIA.checkearColision(jugadorIA2);
             }
        }
        
        for (Observador observador : observadores) {
        	observador.dibujar(this);
        }
        
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
		observadores.add(observador);
	}
}
