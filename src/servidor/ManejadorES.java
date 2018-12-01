package servidor;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ManejadorES {

    private DataInputStream entrada;
    private DataOutputStream salida;
    private Gson gson = new Gson();
    private final Logger logger = Logger.getLogger(ManejadorES.class);

    public ManejadorES(Socket socket) {
        try {
            this.entrada = new DataInputStream(socket.getInputStream());
            this.salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public <T> T escuchar(Class<T> clazz) throws IOException {
        return gson.fromJson(entrada.readUTF(), clazz);
    }

    public <T> void enviar(T mensaje) throws IOException {
        salida.writeUTF(gson.toJson(mensaje));
    }

    public void enviarString(String mensaje) throws IOException {
        salida.writeUTF(mensaje);
    }
}
