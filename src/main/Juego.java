package main;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Juego {

    public static long initTime;

	public static void iniciar() {
		JFrame ventana = new JFrame("Snake");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(0, 0, ANCHO_VENTANA, ALTURA_VENTANA);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		List<Serpiente> serpientes = new ArrayList<>();
		serpientes.add(new Serpiente());

		List<SerpienteIA> serpientesIA = new ArrayList<>();
		serpientesIA.add(new SerpienteIA(20));

        Ui ui = new Ui();
        
        Campo campo = new Campo(serpientes, serpientesIA);

        campo.agregarObservador(ui);
        ventana.setContentPane(ui);
        ventana.setVisible(true);

	}
}
