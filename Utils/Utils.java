package Utils;

import Torneo.Equipo;
import Torneo.Jugador;
import Torneo.Torneo;

public class Utils {
    /**
     * Cuenta la cantidad de equipos no nulos en un array de jugadores.
     * @param equipos Array de equipos. Asume que no es nulo.
     * @return Cantidad de equipos no nulos.
     * @implNote Asume que no hay espacios vacíos entre elementos.
     */
    public static int countNoNulos(Equipo[] equipos) {
        int count = 0;
        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] != null)
                count++;
        }

        return count;
    }

    public static int countNoNulos(Torneo.Partido[][] fechas) {
        int count = 0;
        for (int i = 0; i < fechas.length; i++) {
            if (fechas[i] != null)
                count++;
        }

        return count;
    }

    /**
     * Cuenta la cantidad de jugadores no nulos en un array de jugadores.
     * @param jugadores Array de jugadores. Asume que no es nulo.
     * @return Cantidad de jugadores no nulos.
     * @implNote Asume que no hay espacios vacíos entre elementos.
     */
    public static int countNoNulos(Jugador[] jugadores) {
        int count = 0;
        for (int i = 0; i < jugadores.length; i++)
        {
            if (jugadores[count] != null)
                count++;
        }

        return count;
    }

    public static Equipo findEquipo(Equipo[] equipos, String nombre) {
        Equipo equipo = null;
        int i = 0;
        while (i < equipos.length) {
            if (equipos[i].getNombre().equalsIgnoreCase(nombre)) {
                equipo = equipos[i];
                break;
            }
            i++;
        }

        return equipo;
    }

    // intercambia los valores de dos índices de un arreglo
    public static void swap(Equipo[] arr, int p1, int p2) {
        Equipo temp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = temp;
    }


    public static void selectionSort(Equipo[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int indice_del_menor = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[indice_del_menor].compareTo(arr[j]) > 0) {
                    indice_del_menor = j;
                }
            }
            swap(arr, i, indice_del_menor);
        }
    }

    /**
     * Devuelve un número aleatorio entre min y max, incluyéndolos.
     * @param min el piso del rango.
     * @param max el techo del rango.
     * @return un número aleatorio entre min y max, incluyéndolos.
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
