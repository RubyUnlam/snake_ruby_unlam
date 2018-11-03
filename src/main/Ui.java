package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Ui extends JPanel implements Observador {

    private ImageIcon fondoDefault;
    private String fondoPath = "src/imagenes/fondo.png"; //TODO HACERLO VARIABLE
    private List<Ubicacion> ubicacionesSerpientes = new ArrayList<>();
    private List<Ubicacion> ubicacionesComestibles = new ArrayList<>();
    private List<Puntaje> puntajes = new ArrayList<>();

    Ui() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

	@Override
	public void dibujar(Campo campo) {
		if (this.getKeyListeners().length == 0) {
			addKeyListener(campo);
		}
	    puntajes.addAll(campo.notificarPuntajes());
    	ubicacionesSerpientes.addAll(campo.notificarUbicacionesSerpientes());
        ubicacionesComestibles.addAll(campo.notificarUbicacionesComestibles());
        repaint();
	}

    public void paint(Graphics g){
        fondoDefault = new ImageIcon(fondoPath);
        fondoDefault.paintIcon(this, g, 0, 0);
        if (!ubicacionesSerpientes.isEmpty()) {
            pintarSerpientes(g);
        }
        if (!ubicacionesComestibles.isEmpty()) {
            pintarComestibles(g);
        }
        if (!puntajes.isEmpty()) {
        	pintarPuntajes(g);
        }
        g.dispose();
    }

    private void pintarSerpientes(Graphics g) {
        g.setColor(Color.BLUE); //TODO VER SI USAMOS IMAGENES EN VEZ DE CIRCULOS
        for (Ubicacion serpiente : ubicacionesSerpientes){
            g.fillOval(serpiente.getX(), serpiente.getY(), 20, 20);
        }
        ubicacionesSerpientes.clear();
    }

    private void pintarComestibles(Graphics g) {
        g.setColor(Color.RED); //TODO VER SI USAMOS IMAGENES EN VEZ DE CIRCULOS
        for (Ubicacion comestible : ubicacionesComestibles) {
            g.fillOval(comestible.getX(), comestible.getY(), 20, 20);
        }
        ubicacionesComestibles.clear();
    }

    private void pintarPuntajes(Graphics g) {
        g.setColor(Color.BLACK); //TODO: Elegir una fuente mas linda, y mejorar posicion.
        g.setFont(new Font("ArialBlack", Font.PLAIN, 20)); 
        for (Puntaje puntaje : puntajes) {
        	g.drawString(puntaje.getNombreUsuario(), 30, 30);
        	g.drawString(String.valueOf(puntaje.getPuntuacion()), 30, 55);
        }
        ubicacionesComestibles.clear();
    }
}
