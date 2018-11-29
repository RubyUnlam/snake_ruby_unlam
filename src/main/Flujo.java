package main;

import com.google.gson.Gson;
import servidor.ManejadorES;
import servidor.ManejadorMovimiento;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Flujo extends Thread { //TODO PENSAR EL NOMBRE PARA ESTO

    private SincronizadorDeSalas sincronizadorDeSalas;
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
            e.printStackTrace();
        }
    }

}
