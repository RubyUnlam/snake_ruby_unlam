package main;

import java.util.ArrayList;
import java.util.List;

import servidor.Servidor;

public class Juego {

	public static void main(String[] args) {
		List<Serpiente> serpientes = new ArrayList<>();

		List<Serpiente> serpientesIA = new ArrayList<>();
		
		Servidor servidor = new Servidor(12000, serpientes);

		servidor.start();

        Campo campo = new Campo(serpientes, serpientesIA);

        campo.agregarObservador(servidor.getEscribir());
        
        campo.comenzarJuego();
	}
}
