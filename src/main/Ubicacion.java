package main;

import java.util.Objects;
import java.util.Observable;

public class Ubicacion extends Observable {

	private int x;
	private int y;
	
	public Ubicacion() {
		this.generarUbicacionAleatoria();
	}

	public Ubicacion(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void generarUbicacionAleatoria() {
		double randomX = Math.random() * 770;
		double randomY = Math.random() * 550;
		this.x = (int) (randomX - (randomX % 20));
		this.y = (int) (randomY - (randomY % 20));
	}
	
	/**
	 * Dada la ubicacion siguiente calcula la posicion de atras a la 
	 * ubicacion actual
	 * @param ultima
	 * @param anteUltima
	 * @return
	 */
	public Ubicacion calcularUbicacionAnterior(Ubicacion siguiente) {
		int xUltima = this.getX();
		int yUltima = this.getY();
		int xSiguiente = siguiente.getX();
		int ySiguiente = siguiente.getY();
		
		return new Ubicacion(moverAtras(xUltima, xSiguiente), moverAtras(yUltima, ySiguiente));
	}
	
	/**
	 * Calcula la coordenada anterior a la ultima
	 * @param ultima
	 * @param siguiente
	 * @return
	 */
	private int moverAtras(int ultima, int siguiente) {
		return ultima + (ultima - siguiente);
	}

	@Override
	public String toString() {
		return "Ubicacion [x=" + x + ", y=" + y + "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) o;
        return x == ubicacion.x &&
                y == ubicacion.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}
