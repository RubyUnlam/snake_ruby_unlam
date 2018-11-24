package main;

import servidor.Servidor;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Juego {

	public static void iniciar(Sala sala) {
		

		List<Serpiente> serpientes = new ArrayList<>();
		
		for (int i = 0; i < sala.getCantidadJugadores(); i++) {
			serpientes.add(new Serpiente(Color.BLUE));			
		}
		
		List<SerpienteIA> serpientesIA = new ArrayList<>();			
		for (int i = 0; i < sala.getCantidadIA(); i++) {
			serpientesIA.add(new SerpienteIA(sala.getDificultadIA(), Color.BLACK));
		}
		
//        Ui ui = new Ui();
        
        Campo campo = new Campo(serpientes, serpientesIA);

//        campo.agregarObservador(ui);
//        ventana.setContentPane(ui);

	}

//	private void iniciarServidor() {
//		Servidor servidor = new Servidor(12000, serpientes);
//
//		servidor.start();
//	}

}
