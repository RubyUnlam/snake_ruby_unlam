package main;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import java.util.List;
import static utilidades.Constantes.*;

public class Serpiente {
	
	private Integer id = 1;

	private List<Ubicacion> ubicaciones = new ArrayList<>();
	private int mirandoX;
	private int mirandoY;
	
	private boolean vivo;

	private int velocidad = 20;
	
	Serpiente(){
		vivo = true;
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

	private void crecer() {
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
	
	public boolean checkearColision(Comestible comestible) {
		if (nonNull(comestible))
		System.out.println(ubicaciones.get(0) + " " + comestible.getUbicacion());
		if(nonNull(comestible) && ubicaciones.get(0).equals(comestible.getUbicacion())) {
			crecer();
			return true;
		}
		return false;
	}
	
	public void checkearColision(Serpiente serpiente) { //TODO ESTADOS; MUERTO NO HACE NADA
		Ubicacion cabeza = ubicaciones.get(0);
		List<Ubicacion> cuerpo = serpiente.getUbicaciones();
		Integer idEnemigo = serpiente.getId();
		for (int i = 0; i < cuerpo.size(); i++) {
			Ubicacion actual = cuerpo.get(i);
			if (cabeza.equals(actual)) {
				if (i == 0  && !soyYo(idEnemigo)) {
					serpiente.matar();
				}
				if (soyYo(idEnemigo) && i != 0 || !soyYo(idEnemigo)) { 
					matar();
				} 
				
			}
		}
		
		
	}
	
	private boolean soyYo(Integer id) {
		return this.id.equals(id);
	}
	
	public void matar() {
		vivo = false;
		ubicaciones = new ArrayList<>();
	}
	
	public void mirarDerecha() { 
		if (noEs180GradosEnX(MIRAR_DERECHA)) {
			mirandoX = MIRAR_DERECHA;
			mirandoY = NO_MIRAR;
		}
	}
	
	public void mirarIzquierda() {
		if (noEs180GradosEnX(MIRAR_IZQUIERDA)) {
			mirandoX = MIRAR_IZQUIERDA;
			mirandoY = NO_MIRAR;			
		}
	}
	
	public void mirarArriba() {
		if (noEs180GradosEnY(MIRAR_ARRIBA)) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ARRIBA;			
		}
	}
	
	public void mirarAbajo() {
		if (noEs180GradosEnY(MIRAR_ABAJO)) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ABAJO;			
		}
	}
	
	private boolean noEs180GradosEnX(int direccion) {
		int cabeza = ubicaciones.get(0).getX();
		int cuello = ubicaciones.get(1).getX();
		return ((cabeza + (direccion * velocidad)) != cuello);
	}
	
	private boolean noEs180GradosEnY(int direccion) {
		int cabeza = ubicaciones.get(0).getY();
		int cuello = ubicaciones.get(1).getY();
		return ((cabeza + (direccion * velocidad)) != cuello);
	}
	
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}
	
	public Integer getId() {
		return id;
	}
	
}
