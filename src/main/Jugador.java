package main;

import java.awt.*;
import java.net.Socket;

public class Jugador {

    private String nombre;
    private transient Socket conexion;
    private Color color = Color.BLUE;

    public Jugador(String nombre, Socket conexion) {
        this.nombre = nombre;
        this.conexion = conexion;
        this.color = color;
    }

    public Socket getConexion() {
        return conexion;
    }

    public Color getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
