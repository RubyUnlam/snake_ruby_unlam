package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import static java.util.Objects.nonNull;

public class Campo extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 100;
	private ImageIcon fondoDefault;
	private String fondoPath = "src/imagenes/fondo.png"; //TODO HACERLO VARIABLE
	
	
	private List<Serpiente> jugadores;
	private List<Serpiente> serpientesIA;
	private Comestible manzana;
	
	private int keyEventUP = KeyEvent.VK_UP;
	private int keyEventDOWN = KeyEvent.VK_DOWN;
	private int keyEventRIGTH = KeyEvent.VK_RIGHT;
	private int keyEventLEFT = KeyEvent.VK_LEFT;
	
	Campo(List<Serpiente> jugadores, List<Serpiente> serpientesIA) {
		this.jugadores = jugadores;
		this.serpientesIA = serpientesIA;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g){
		fondoDefault = new ImageIcon(fondoPath);
		fondoDefault.paintIcon(this, g, 0, 0);
		pintarSerpiente(g);
		pintarManzana(g);
		g.dispose();
	}
	
	private void pintarSerpiente(Graphics g) {
		g.setColor(Color.BLUE); //TODO VER SI USAMOS IMAGENES EN VEZ DE CIRCULOS
		for (Serpiente jugador : jugadores) {
			for(Ubicacion actual : jugador.getUbicaciones()) {
				g.fillOval(actual.getX(), actual.getY(), 20, 20);
			}
		}
	}
	
	private void pintarManzana(Graphics g) {
		g.setColor(Color.RED); //TODO VER SI USAMOS IMAGENES EN VEZ DE CIRCULOS
		if (manzana == null) { //TODO AGREGAR VALIDACION PARA QUE NO SPAWNEE ENCIMA DE LA SERPIENTE
			manzana = new Manzana();
		}
		g.fillOval(manzana.getUbicacion().getX(), manzana.getUbicacion().getY(), 20, 20);			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Serpiente jugador : jugadores) {
			jugador.moverse();
		}
		
		for (Serpiente jugador : jugadores) {
			for (Serpiente jugador2 : jugadores) {
				jugador.checkearColision(jugador2);
				if (jugador.checkearColision(manzana)) {
					manzana = null;
				}
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) { //TODO VER COMO FUNCIONARIA ESTO EN MULTIJUGADOR
		int teclaPresionada = e.getKeyCode();
		if (teclaPresionada == keyEventUP) {
			jugadores.get(0).mirarArriba();
		} else if (teclaPresionada == keyEventDOWN) {
			jugadores.get(0).mirarAbajo();
		} else if (teclaPresionada == keyEventRIGTH) {
			jugadores.get(0).mirarDerecha();
		} else if (teclaPresionada == keyEventLEFT) {
			jugadores.get(0).mirarIzquierda();
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
