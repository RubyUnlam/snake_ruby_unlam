package utilidades;

import java.awt.*;

public enum NombreIA {

    SOLID ("Solid", Color.YELLOW),
    LIQUID ("Liquid", Color.PINK),
    SOLIDUS ("Solidus", Color.GRAY),
    NAKED ("Naked", Color.CYAN),
    SEVERUS ("Severus", Color.WHITE),
    SNEAKY ("Sneaky", Color.MAGENTA);

    private final String nombre;
    private final Color color;

    NombreIA(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String nombre() {
        return nombre;
    }

    public Color color() {
        return color;
    }

}
