package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Ui extends JPanel implements Observador {

    private ImageIcon fondoDefault;
    private String fondoPath = "src/imagenes/fondo.png"; //TODO HACERLO VARIABLE
    private List<Ubicacion> ubicacionesSerpientes = new ArrayList<>();
    private List<Ubicacion> ubicacionesComestibles= new ArrayList<>();

    Ui() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

	@Override
	public void dibujar(Campo campo) {
		if (this.getKeyListeners().length == 0) {
			addKeyListener(campo);
		}
	    
    	ubicacionesSerpientes.addAll(campo.notificarUbicacionesSerpientes());
        ubicacionesComestibles.addAll(campo.notificarUbicacionesComestibles());
        repaint();
	}

    public void paint(Graphics g){
        fondoDefault = new ImageIcon(fondoPath);
        fondoDefault.paintIcon(this, g, 0, 0);
//        g.setColor(Color.RED);
//        g.drawRect(0, 0, 798, 790);
        if (!ubicacionesSerpientes.isEmpty()) {
            pintarSerpientes(g);
        }
        if (!ubicacionesComestibles.isEmpty()) {
            pintarComestibles(g);
        }
        g.dispose();
    }

    private void pintarSerpientes(Graphics g) {
        g.setColor(Color.BLUE); //TODO VER SI USAMOS IMAGENES EN VEZ DE CIRCULOS
        for (Ubicacion serpiente : ubicacionesSerpientes){
        	System.out.println(serpiente);
            g.fillOval(serpiente.getX(), serpiente.getY(), 20, 20);
        }
        ubicacionesSerpientes.removeAll(ubicacionesSerpientes);
    }

    private void pintarComestibles(Graphics g) {
        g.setColor(Color.RED); //TODO VER SI USAMOS IMAGENES EN VEZ DE CIRCULOS
        for (Ubicacion comestible : ubicacionesComestibles) {
            g.fillOval(comestible.getX(), comestible.getY(), 20, 20);
        }
        ubicacionesComestibles.removeAll(ubicacionesComestibles);
    }

}
