package main;

import java.net.Socket;
import java.util.List;

public interface Observador {

	void dibujar(List<Dibujable> dibujable);
	void agregarJugador(Socket jugador);
	
}
