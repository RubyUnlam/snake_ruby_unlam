package juego.comestible;

import java.awt.*;

public class Pera extends Comestible {

    private final static Color COLOR = Color.GREEN;
    private final static String POWER_UP = "inmortal";
    private final static int PUNTAJE = 0;

    public Pera() {
        super(COLOR, PUNTAJE, POWER_UP);
    }

}
