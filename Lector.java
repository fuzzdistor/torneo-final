import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;


public class Lector {

    public static String[][] getContenidos(String ruta) {
        return getContenidos(ruta, ";");
    }

    public static String[][] getContenidos(String ruta, String separador) {
        String linea;
        // Voy a inicializarlo con el tamaño adecuado más adelante. Este es solo un valor por defecto y no debería llegar a usarse.
        String[][] elementos = new String[0][];
        try {
            FileReader fileReader = new FileReader(ruta);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            long nroLineas = bufferedReader.lines().count();

            // Lo inicializo ahora que tengo la cantidad de equipos
            elementos = new String[(int) nroLineas][];

            // Leo hasta que me quede sin líneas
            int i = 0;
            while ((linea = bufferedReader.readLine()) != null) {
                elementos[i] = linea.split(separador);
                i++;
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {    // Posible error en el constructor de FileReader
            System.err.println(ex.getMessage() + "no se encontró el archivo o no puede ser abierto para lectura");
        } catch (UncheckedIOException ex) {     // Posible error en bufferedReader.lines
            System.err.println(ex.getMessage() + "error de lectura");
        } catch (IOException ex) {              // Posible error en bufferedReader.readLine
            System.err.println(ex.getMessage() + "error de lectura");
        }

        return elementos;
    }
}
