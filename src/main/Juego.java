package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Juego {

	public static void main(String[] args) {
		JFrame ventana = new JFrame("Sarasa");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(0, 0, 800, 600);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		
		List<Serpiente> serpientes = new ArrayList<>();
		serpientes.add(new Serpiente());
		
		List<Serpiente> serpientesIA = new ArrayList<>();
		
		ventana.setContentPane(new Campo(serpientes, serpientesIA)); //TODO NO CREAR SERPIENTE A LO CABEZA
		ventana.setVisible(true);
	}
}
