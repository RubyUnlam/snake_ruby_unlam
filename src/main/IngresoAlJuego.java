package main;

import com.google.gson.Gson;
import servidor.ManejadorES;

import java.io.IOException;

import static java.util.Objects.isNull;

public class IngresoAlJuego {

    private ManejadorES manejadorES;

    public IngresoAlJuego(ManejadorES manejadorES) {
        this.manejadorES = manejadorES;
    }

    public Usuario ingresar() throws IOException {

        boolean sesionIniciada = false;

        Usuario usuario;
        do {
            RegistroUsuario registroUsuario;
            usuario = manejadorES.escuchar(Usuario.class);
            if (isNull(usuario.getEmail())) {
                boolean registroValido = Sesion.iniciarSesion(usuario);
                if (!registroValido) {
                    registroUsuario = new RegistroUsuario("login invalido", registroValido);
                } else {
                    registroUsuario = new RegistroUsuario("", registroValido);
                    sesionIniciada = true;
                }
            } else {
                registroUsuario = Sesion.registrarUsuario(usuario.getNombreUsuario(), usuario.getContrasenia(), usuario.getEmail());
            }
            manejadorES.enviar(registroUsuario);
        } while (!sesionIniciada);
        return usuario;
    }
}
