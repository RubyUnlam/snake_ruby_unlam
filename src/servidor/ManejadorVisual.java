package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.Dibujable;
import main.Observador;

public class ManejadorVisual implements Observador {

    private List<Socket> sockets = new ArrayList<>();
    private List<DataOutputStream> salidas = new ArrayList<>();
    private Gson gson = new Gson();

    public void dibujar(List<Dibujable> dibujable) {
        try {
            for (DataOutputStream entrada : salidas) {
                entrada.writeUTF(gson.toJson(dibujable));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregarJugador(Socket jugador) {
        try {
            sockets.add(jugador);
            salidas.add(new DataOutputStream(jugador.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
