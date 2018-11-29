package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ManejadorES {

    private DataInputStream entrada;
    private DataOutputStream salida;

    public ManejadorES(Socket socket) {
        try {
            this.entrada = new DataInputStream(socket.getInputStream());
            this.salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String escuchar() throws IOException {
        return entrada.readUTF();
    }

    public void enviar(String mensaje) throws IOException {
        salida.writeUTF(mensaje);
    }
}
