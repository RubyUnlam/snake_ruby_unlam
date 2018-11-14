package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Ui extends JPanel implements Observador {

	private static final long serialVersionUID = 1L;
	private ImageIcon fondoDefault;
    private String fondoPath = "src/imagenes/fondo.png"; //TODO HACERLO VARIABLE
    private List<Dibujable> aDibujar = new ArrayList<>();

    Ui() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

	@Override
	public void dibujar(Campo campo) {
		if (this.getKeyListeners().length == 0) {
			addKeyListener(campo);
		}
	    
		aDibujar.addAll(campo.notificarDibujables());
        repaint();
	}

    public void paint(Graphics g){
        fondoDefault = new ImageIcon(fondoPath);
        fondoDefault.paintIcon(this, g, 0, 0);
        if (!aDibujar.isEmpty()) {
            dibujar(g);
        }
        g.dispose();
    }

    /**
     * Dibuja un circulo de 20x20 por cada dibujable en la lista
     * aDibujar y luego limpia la lista
     */
    private void dibujar(Graphics g) {
    	for (Dibujable dibujable : aDibujar) {
    		List<Ubicacion> ubicaciones = dibujable.obtenerZonaDeDibujo();
    		if (!ubicaciones.isEmpty()) {
    			Color colorActual = dibujable.obtenerColor();
        		g.setColor(colorActual.darker());
        		g.fillOval(ubicaciones.get(0).getX(), ubicaciones.get(0).getY(), 20, 20);
        		for (int i = 1; i < ubicaciones.size(); i++) {
        			g.setColor(colorActual);
        			g.fillOval(ubicaciones.get(i).getX(), ubicaciones.get(i).getY(), 20, 20);
        		}
    		}
    	}
    	aDibujar.clear();
    }

}
