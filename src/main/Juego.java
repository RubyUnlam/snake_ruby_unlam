package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import test.SerpienteTest;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;

public class Juego {

    public static long initTime;

	public static void main(String[] args) {
		JFrame ventana = new JFrame("Snake");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(0, 0, ANCHO_VENTANA, ALTURA_VENTANA);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		
		List<Serpiente> serpientes = new ArrayList<>();
		serpientes.add(new Serpiente());

		List<Serpiente> serpientesIA = new ArrayList<>();
		serpientesIA.add(new SerpienteIA(100));
		
        Ui ui = new Ui();

        Campo campo = new Campo(serpientes, serpientesIA);

        campo.agregarObservador(ui);

        ventana.setContentPane(ui); //TODO NO CREAR SERPIENTE A LO CABEZA
		ventana.setVisible(true);
	}
}
