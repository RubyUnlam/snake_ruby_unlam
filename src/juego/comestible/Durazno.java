package juego.comestible;

import java.awt.*;

public class Durazno extends Comestible {

    private final static Color COLOR = Color.ORANGE;
    private final static String POWER_UP = "congelado";
    private final static int PUNTAJE = 50;

    public Durazno() {
        super(COLOR, PUNTAJE, POWER_UP);
    }

}
