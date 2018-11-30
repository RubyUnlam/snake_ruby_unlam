package main;

import org.slf4j.Logger;
import org.slf4j.impl.SimpleLoggerFactory;
import servidor.ManejadorES;

import java.io.IOException;
import java.net.Socket;

public class Flujo extends Thread { //TODO PENSAR EL NOMBRE PARA ESTO

    private SincronizadorDeSalas sincronizadorDeSalas;
    private Logger logger =  new SimpleLoggerFactory().getLogger("Flujo");
    private ManejadorES manejadorES;
    private Socket conexion;


    public Flujo(Socket conexion, SincronizadorDeSalas sincronizadorDeSalas) {
        this.conexion = conexion;
        this.manejadorES = new ManejadorES(conexion);
        this.sincronizadorDeSalas = sincronizadorDeSalas;
    }

    @Override
    public void run() {
        try {
            Usuario usuario = new IngresoAlJuego(manejadorES).ingresar();

            new MenuPrincipal(usuario, manejadorES, sincronizadorDeSalas).jugar();

            conexion.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
