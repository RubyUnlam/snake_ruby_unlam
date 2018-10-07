package main;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Juego {

	public static void main(String[] args) {
		JFrame ventana = new JFrame("Sarasa");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(0, 0, 800, 600);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setContentPane(new Campo(new Serpiente())); //TODO NO CREAR SERPIENTE A LO CABEZA
		ventana.setVisible(true);
	}
}
