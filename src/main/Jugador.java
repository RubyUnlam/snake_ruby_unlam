package main;

import com.google.gson.Gson;
import servidor.ManejadorES;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Jugador {

    private Gson gson = new Gson();
    private String nombre;
    private transient ManejadorES manejadorES;
    private Color color = Color.BLUE;
    private transient boolean estaListo;
    private transient CountDownLatch escuchandoTeclas;

    public Jugador(String nombre, ManejadorES manejadorES) {
        this.nombre = nombre;
        this.manejadorES = manejadorES;
//        this.color = color;
    }

    public ManejadorES getManejador() {
        return manejadorES;
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
            manejadorES.enviar(gson.toJson(sala));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getEstaListo() {
        return estaListo;
    }

    public void setEstaListo(boolean estaListo) {
        this.estaListo = estaListo;
    }

    public void cerrarActualizacionDeSala() {
        try {
            manejadorES.enviar("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CountDownLatch obtenerEscuchandoTeclas() {
        return escuchandoTeclas;
    }

    public void setEscuchandoTeclas(CountDownLatch escuchandoTeclas) {
        this.escuchandoTeclas = escuchandoTeclas;
    }
}
