package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Lector {

    /**
     * Abre, lee y devuelve los contenidos de un archivo como String
     * @param ruta La ruta del archivo para abrir y leer
     * @return Un String con los contenidos del archivo. Si surge un error devuelve una cadena vacía.
     */
    public static String getContenidos(String ruta) {
        StringBuilder contenidos = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(ruta);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Leo hasta que me quede sin líneas
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                contenidos.append(linea);
                contenidos.append("\n");
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {    // Posible error en el constructor de FileReader
            System.err.println(ex.getMessage() + "no se encontró el archivo o no puede ser abierto para lectura");
        } catch (IOException ex) {              // Posible error en bufferedReader.readLine
            System.err.println(ex.getMessage() + "error de lectura");
        }

        return contenidos.toString();
    }
}
