package main;

import java.util.ArrayList;
import java.util.List;

public class Serpiente {
	
	private List<Ubicacion> ubicaciones = new ArrayList<>();
	private int mirandoX;
	private int mirandoY;
	private int velocidad = 20;
	
	Serpiente(){
		ubicaciones.add(new Ubicacion(50, 90)); //TODO ELIMINAR UBICACIONES DEFAULT
		ubicaciones.add(new Ubicacion(50, 70));
		ubicaciones.add(new Ubicacion(50, 50));
		ubicaciones.add(new Ubicacion(50, 30));
		ubicaciones.add(new Ubicacion(50, 10));
		mirarAbajo();
	}
	
	public void moverse() {
		for(int i = ubicaciones.size() - 1 ; i >= 0 ; i--) {
			if (i != 0) {
				ubicaciones.set(i, ubicaciones.get(i-1));
			} else {
				Ubicacion cabeza = ubicaciones.get(i);
				int x = mirandoX != 0 ? cabeza.getX() + (mirandoX * velocidad) : cabeza.getX();
				int y = mirandoY != 0 ? cabeza.getY() + (mirandoY * velocidad) : cabeza.getY();
				
				ubicaciones.set(0, new Ubicacion(x,y));
			}	
		}
	}
	
	public void crecer() {
		
	}
	
	public void mirarDerecha() { //TODO VALIDAR LOS MOVIMIENTOS
		mirandoX = 1;
		mirandoY = 0;
	}
	
	public void mirarIzquierda() {
		mirandoX = -1;
		mirandoY = 0;
	}
	
	public void mirarArriba() {
		mirandoX = 0;
		mirandoY = -1;
	}
	
	public void mirarAbajo() {
		mirandoX = 0;
		mirandoY = 1;
	}
	
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}

}
