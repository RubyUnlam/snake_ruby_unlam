package utilidades;

import excepciones.ErrorConfiguracionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Configuracion {

    private final String pathConfiguracion = "src/configuracion/";

    private int puerto;

    public Configuracion(String path) throws ErrorConfiguracionException {
        try {
            Scanner scanner = new Scanner(new File(pathConfiguracion + path));
            this.puerto = scanner.nextInt();
        } catch (FileNotFoundException e) {
            throw new ErrorConfiguracionException("Error levantando la configuracion, asegurese de que el archivo existe y tiene el formato correcto");
        }
    }

    public int getPuerto() {
        return puerto;
    }

}
