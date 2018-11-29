package main;

import servidor.ManejadorES;

import java.net.Socket;
import java.util.List;

public interface Observador {

	void dibujar(ActualizacionDelJuego actualizacion);
	void agregarJugador(ManejadorES manejadorDelJugador);
	
}
