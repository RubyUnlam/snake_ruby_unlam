package servidor;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.Observador;
import main.UbicacionesDTO;

public class ChatEscrituraServidor implements Observador {

	private List<Socket> sockets = new ArrayList<>();
	private List<DataOutputStream> entradas = new ArrayList<>();
	private Gson gson = new Gson();
    
    public void dibujar(UbicacionesDTO ubicaciones) {
    	for (DataOutputStream entrada : entradas) {
            try {
				entrada.writeUTF(gson.toJson(ubicaciones));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    @Override
    public void agregarJugador(Socket jugador) {
		try {
			sockets.add(jugador);
        	entradas.add(new DataOutputStream(jugador.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
