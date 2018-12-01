package juego.comestible;

import java.awt.*;

public class Ciruela extends Comestible {

    private final static Color COLOR = Color.MAGENTA;
    private final static String POWER_UP = "dividir";
    private final static int PUNTAJE = 5;

    public Ciruela() {
        super(COLOR, PUNTAJE, POWER_UP);
    }
}
