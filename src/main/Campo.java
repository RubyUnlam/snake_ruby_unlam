package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Campo extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 100;
	private ImageIcon fondoDefault;
	private String fondoPath = "src/imagenes/fondo.png"; //TODO HACERLO VARIABLE
	private Serpiente jugador;
	private Comestible manzana;
	
	private int keyEventUP = KeyEvent.VK_UP;
	private int keyEventDOWN = KeyEvent.VK_DOWN;
	private int keyEventRIGTH = KeyEvent.VK_RIGHT;
	private int keyEventLEFT = KeyEvent.VK_LEFT;
	
	Campo(Serpiente serpiente) {
		jugador = serpiente;
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
		for(Ubicacion actual : jugador.getUbicaciones()) {
			g.fillOval(actual.getX(), actual.getY(), 20, 20);
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
		jugador.moverse();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int teclaPresionada = e.getKeyCode();
		if (teclaPresionada == keyEventUP) {
			jugador.mirarArriba();
		} else if (teclaPresionada == keyEventDOWN) {
			jugador.mirarAbajo();
		} else if (teclaPresionada == keyEventRIGTH) {
			jugador.mirarDerecha();
		} else if (teclaPresionada == keyEventLEFT) {
			jugador.mirarIzquierda();
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
