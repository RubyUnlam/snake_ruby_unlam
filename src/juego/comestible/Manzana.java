package juego.comestible;

import juego.Ubicacion;

import java.awt.Color;

public class Manzana extends Comestible {

    private final static Color COLOR = Color.RED;
    private final static String POWER_UP = "";
    private final static int PUNTAJE = 10;

    public Manzana() {
        super(COLOR, PUNTAJE, POWER_UP);
    }

    public Manzana(Ubicacion ubicacion) {
        super(ubicacion, COLOR);
    }

}
