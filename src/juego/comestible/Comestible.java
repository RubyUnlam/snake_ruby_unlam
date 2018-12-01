package juego.comestible;

import juego.Ubicacion;

import java.awt.Color;

public abstract class Comestible {

    private Color color;
    private Ubicacion ubicacion;
    private boolean comida = false;
    private int puntaje;
    private String powerUp;

    Comestible(Color color, int puntaje, String powerUp) {
        this.ubicacion = new Ubicacion();
        this.color = color;
        this.puntaje = puntaje;
        this.powerUp = powerUp;
    }

    Comestible(Ubicacion ubicacion, Color color) {
        this.ubicacion = ubicacion;
        this.color = color;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public boolean fueComida() {
        return comida;
    }

    public void setComida(boolean comida) {
        this.comida = comida;
    }

    public Color obtenerColor() {
        return this.color;
    }

    public int obtenerPuntaje() {
        return puntaje;
    }

    public String getPowerUp() {
        return powerUp;
    }
}
