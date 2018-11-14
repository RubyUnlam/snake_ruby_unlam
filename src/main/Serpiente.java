package main;

import static utilidades.Constantes.VELOCIDAD;

import java.util.ArrayList;
import java.util.List;

public class Serpiente{

	protected Estado estado;
	private Colision verificadorColisiones;
	
	List<Ubicacion> ubicaciones = new ArrayList<>();
	protected Direccion direccion;
	
	public Serpiente(){
		estado = new Normal();
		verificadorColisiones = new Colision();
		Ubicacion cabeza = new Ubicacion();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + VELOCIDAD, cabeza.getY()));
		crecer();
		crecer();
		this.direccion = Direccion.IZQUIERDA;
	}
	
	public Serpiente(Ubicacion cabeza){
		estado = new Normal();
		verificadorColisiones = new Colision();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + VELOCIDAD, cabeza.getY()));
		crecer();
		crecer();
		this.direccion = Direccion.IZQUIERDA;
	}

	public void morir() {
		estado = estado.morir(this);
	}
	
	public void moverse() {
		estado = estado.moverse(this);
	}

	protected void crecer() {
		int cola = ubicaciones.size() - 1;
		Ubicacion uCola = ubicaciones.get(cola);
		Ubicacion uAnteUltimo = ubicaciones.get(cola - 1);
		int nuevoX = calcularPosicionAnterior(uCola.getX(), uAnteUltimo.getX());
		int nuevoY = calcularPosicionAnterior(uCola.getY() ,uAnteUltimo.getY());
		ubicaciones.add(new Ubicacion(nuevoX, nuevoY));
	}
	
	private int calcularPosicionAnterior(int ultimaPos, int anteUltimaPos) {
		return ultimaPos + (ultimaPos - anteUltimaPos);
	}
	
	public void comprobarColision(Comestible comestible) {
		verificadorColisiones.comprobarColision(this, comestible);
	}
	
	public void comprobarColision(Serpiente serpiente) {
		verificadorColisiones.comprobarColision(this, serpiente);
	}
	
	public void mirar(String mirarA) {
		this.direccion = direccion.cambiarDireccion(mirarA, this);
	}	
	
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}
	
	//Para SerpientaIA 
		
	protected Ubicacion getUbicacionCabeza() {
		return  ubicaciones.get(0);
	}
	
	public boolean estaMuerto() {
		return ubicaciones.isEmpty();
	}
}
