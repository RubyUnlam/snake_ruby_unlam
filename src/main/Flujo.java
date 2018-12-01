package main;

import servidor.ManejadorES;
import servidor.SincronizadorUsuariosLoggeados;
import org.slf4j.Logger;
import org.slf4j.impl.SimpleLoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Flujo extends Thread { //TODO PENSAR EL NOMBRE PARA ESTO

    private SincronizadorDeSalas sincronizadorDeSalas;
    private Logger logger =  new SimpleLoggerFactory().getLogger("Flujo");
    private ManejadorES manejadorES;
    private Socket conexion;
    private SincronizadorUsuariosLoggeados sincronizadorUsuariosLoggeados;


    public Flujo(Socket conexion, SincronizadorDeSalas sincronizadorDeSalas, SincronizadorUsuariosLoggeados sincronizadorUsuariosLoggeados) {
        this.conexion = conexion;
        this.manejadorES = new ManejadorES(conexion);
        this.sincronizadorDeSalas = sincronizadorDeSalas;
        this.sincronizadorUsuariosLoggeados = sincronizadorUsuariosLoggeados;
    }

    @Override
    public void run() {
        try {
            Usuario usuario = new IngresoAlJuego(manejadorES, sincronizadorUsuariosLoggeados).ingresar();

            new MenuPrincipal(usuario, manejadorES, sincronizadorDeSalas).jugar();

            sincronizadorUsuariosLoggeados.eliminarJugador(usuario.getNombreUsuario());

            conexion.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
