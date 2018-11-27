package main;

import com.google.gson.Gson;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Jugador {

    private String nombre;
    private transient Socket conexion;
    private Color color = Color.BLUE;
    private transient DataOutputStream dataOutputStream;

    public Jugador(String nombre, Socket conexion) {
        this.nombre = nombre;
        this.conexion = conexion;
        this.color = color;
        try {
            this.dataOutputStream = new DataOutputStream(conexion.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void notificarActualizacionDeSala(Sala sala) {
        try {
            dataOutputStream.writeUTF(new Gson().toJson(sala));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarActualizacionDeSala() {
        try {
            dataOutputStream.writeUTF("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
