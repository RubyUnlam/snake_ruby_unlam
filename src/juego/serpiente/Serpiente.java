package juego.serpiente;

import juego.Cuadrante;
import juego.Ubicacion;
import juego.comestible.Comestible;
import juego.serpiente.Estado;

import static utilidades.Constantes.VELOCIDAD;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Serpiente implements Comparable<Serpiente>{

	private Estado estado;
	private Color color;
	private List<Ubicacion> ubicaciones = new ArrayList<>();

	protected Direccion direccion;
	private int puntaje;
	private String nombreDeSerpiente;
	private boolean salir;

	public Serpiente(Color color, String nombre, Cuadrante cuadrante){
		this.color = color;
		estado = new Normal();
		Ubicacion cabeza = new Ubicacion(cuadrante);
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + VELOCIDAD, cabeza.getY()));
		crecer();
		crecer();
		this.direccion = Direccion.IZQUIERDA;
		this.nombreDeSerpiente = nombre;
	}

	public Serpiente(Ubicacion cabeza){
		estado = new Normal();
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

	public void comer(Comestible comestible) {
		sumarPuntos(comestible.obtenerPuntaje());
		crecer();
		powerUp(comestible);
	}

	private void powerUp(Comestible comestible) {
		switch (comestible.getPowerUp()) {
			case "congelado":
				new GestorDeEstado(this, new Congelado(), 2000).start();
				break;
			case "inmortal":
				new GestorDeEstado(this, new Inmortal(), 3000).start();
				break;
			case "confundido":
				new GestorDeEstado(this, new Confundido(), 2500).start();
				break;
			case "invertir":
				Collections.reverse(this.ubicaciones);
				for (Direccion direccion : Direccion.values()){
					Direccion nueva = this.direccion.cambiarDireccion(direccion.name(), this);
					if (!this.direccion.equals(nueva)) {
						this.direccion = nueva;
						break;
					}
				}
				break;
			case "dividir":
				if(ubicaciones.size() > 3){
					ubicaciones = ubicaciones.subList(0,ubicaciones.size()/2);
				}
				break;
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

	public void mirar(String mirarA) {
		this.direccion = estado.mirarA(this, mirarA);
	}

	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}

	public void setUbicaciones( ArrayList<Ubicacion> ubicaciones ) {
		this.ubicaciones = ubicaciones;
	}

	public Color obtenerColor() {
		return this.color;
	}

	protected Ubicacion getUbicacionCabeza() {
		return  ubicaciones.get(0);
	}

	public boolean estaMuerto() {
		return ubicaciones.isEmpty();
	}

	public Estado getEstado() {
		return estado;
	}

	public String getNombre(){ return nombreDeSerpiente;}

	public Integer getPuntaje(){ return puntaje;}

	private void sumarPuntos(int puntos){
		puntaje += puntos;
	}

    public boolean salir() {
        return salir;
    }

    public Serpiente setSalir(boolean salir) {
        this.salir = salir;
        return this;
    }

	@Override
	public int compareTo(Serpiente o) {
		return o.getPuntaje() - this.getPuntaje();
	}

	protected void setEstado(Estado estado) {
		this.estado = estado;
	}
}