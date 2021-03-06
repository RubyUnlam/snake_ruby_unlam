package juego;

import java.io.Serializable;
import java.util.Objects;
import java.util.Observable;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;

public class Ubicacion extends Observable implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;

    public Ubicacion() {
        this.generarUbicacionAleatoria();
    }

    public Ubicacion(Cuadrante cuadrante) {
        this.generarUbicacionAleatoria(cuadrante.getX(), cuadrante.getY());
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

    public void generarUbicacionAleatoria() {
        double randomX = Math.random() * 770;
        double randomY = Math.random() * 550;
        this.x = (int) (randomX - (randomX % 20));
        this.y = (int) (randomY - (randomY % 20));
    }

    public void generarUbicacionAleatoria(int cuadranteX, int cuadranteY) {
        double maxX = ANCHO_VENTANA / 2.;
        double maxY = ALTURA_VENTANA / 2.;

        double randomX = Math.random() * maxX;
        double randomY = Math.random() * maxY;
        this.x = (int) ((randomX - (randomX % 20)) + (maxX * cuadranteX));
        this.y = (int) ((randomY - (randomY % 20)) + (maxY * cuadranteY));
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
