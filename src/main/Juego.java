package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;

public class Juego {

    public static long initTime;
    // TODO: El metodo recibe el usuario que se loggeo. Esto hay que revisarlo para cuando
    // agreguemos cliente servidor. Podria pensarse que desde la sala vienen todos los usuarios,
    // y asi creamos la serpiente y sus puntuaciones.
	public static void iniciar(String usuario) {
		JFrame ventana = new JFrame("Snake");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(0, 0, ANCHO_VENTANA, ALTURA_VENTANA);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		List<Serpiente> serpientes = new ArrayList<>();
//		serpientes.add(new Serpiente());
		//Se agrega a la serpiente el usuario obtenido desde el login.
		serpientes.add(new Serpiente(usuario));

		List<Serpiente> serpientesIA = new ArrayList<>();
		
        Ui ui = new Ui();
        
        Campo campo = new Campo(serpientes, serpientesIA);

        campo.agregarObservador(ui);
        ventana.setContentPane(ui);
        ventana.setVisible(true);

	}
}
