package main;

import excepciones.ErrorConfiguracionException;
import servidor.Servidor;
import utilidades.Configuracion;

import static main.SesionSingleton.getSessionFactory;

public class Main {

    public static void main(String[] args) throws ErrorConfiguracionException {
        // lo primero que hago antes del levantar el servidor es crear el session factory
        // para evitar penalizar al primer jugador en loguear con la espera de creacion de este
        Configuracion configuracion = new Configuracion("configuracion");
        new Servidor(configuracion.getPuerto()).start();
        getSessionFactory();
    }
}
