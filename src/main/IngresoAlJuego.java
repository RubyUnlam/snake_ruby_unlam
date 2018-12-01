package main;

import servidor.ManejadorES;
import servidor.SincronizadorUsuariosLoggeados;

import java.io.IOException;

import static java.util.Objects.isNull;

public class IngresoAlJuego {

    private ManejadorES manejadorES;
    private SincronizadorUsuariosLoggeados sincronizadorUsuariosLoggeados;
    private final String SALIR = "salir";

    public IngresoAlJuego(ManejadorES manejadorES, SincronizadorUsuariosLoggeados sincronizadorUsuariosLoggeados) {
        this.manejadorES = manejadorES;
        this.sincronizadorUsuariosLoggeados = sincronizadorUsuariosLoggeados;
    }

    public Usuario ingresar() throws IOException {

        boolean sesionIniciada = false;

        Usuario usuario;
        String mensaje;
        do {
            RegistroUsuario registroUsuario;
            mensaje = manejadorES.escuchar(String.class);
            if (SALIR.equals(mensaje)) {
                return null;
            }
            usuario = manejadorES.escuchar(Usuario.class);
            if (sincronizadorUsuariosLoggeados.estaLoggeado(usuario.getNombreUsuario())) {
                registroUsuario = new RegistroUsuario("Usuario loggeado", false);
            } else {
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
            }
            manejadorES.enviar(registroUsuario);
        } while (!sesionIniciada);
        sincronizadorUsuariosLoggeados.insertarJugador(usuario.getNombreUsuario());
        return usuario;
    }
}
