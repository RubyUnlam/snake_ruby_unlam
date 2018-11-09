package main;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Juego {

    public static long initTime;

	public static void iniciar(Sala sala) {
		JFrame ventana = new JFrame("Snake");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(0, 0, ANCHO_VENTANA, ALTURA_VENTANA);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		List<Serpiente> serpientes = new ArrayList<>();
		
		for (int i = 0; i < sala.getCantidadJugadores(); i++) {
			serpientes.add(new Serpiente());			
		}
		
		List<SerpienteIA> serpientesIA = new ArrayList<>();			
		for (int i = 0; i < sala.getCantidadIA(); i++) {
			serpientesIA.add(new SerpienteIA(sala.getDificultadIA()));
		}
		
        Ui ui = new Ui();
        
        Campo campo = new Campo(serpientes, serpientesIA);

        campo.agregarObservador(ui);
        ventana.setContentPane(ui);
        ventana.setVisible(true);

	}
}
