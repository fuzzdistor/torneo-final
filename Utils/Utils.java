package Utils;

import Torneo.Equipo;
import Torneo.Jugador;
import Torneo.Torneo;

public class Utils {
    /**
     * Cuenta la cantidad de equipos no nulos en un array de jugadores.
     *
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

    public static Jugador[] generarJugadoresRandom(int n) {
        Jugador[] ju = new Jugador[n];

        for (int i = 0; i < n; i++) {
            //esto es para generar un nombre y apellido cualquiera
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder sb = new StringBuilder(20);
            String apellido;
            String nombre;

            //genero un apellido cualquiera
            sb.setLength(0);
            for (int j = 0; j < (int) (Math.random() * 6) + 5; j++) {
                sb.append(chars.charAt((int) (Math.random() * chars.length())));
            }
            apellido = sb.toString();

            //genero un nombre cualquiera
            sb.setLength(0);
            for (int j = 0; j < (int) (Math.random() * 6) + 5; j++) {
                sb.append(chars.charAt((int) (Math.random() * chars.length())));
            }
            nombre = sb.toString();

            //creo el jugador
            ju[i] = new Jugador(apellido, nombre, 1, 1, 1);
        }

        return ju;
    }

    public static boolean hasJugador(Jugador[] jugadores, Jugador j) {
        boolean tiene = false;
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] != null && jugadores[i].equals(j)) {
                tiene = true;
                break;
            }
        }

        return tiene;
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

    public static boolean copyArrayEn(Jugador[] lhs, Jugador[] rhs) {
        boolean exito = false;
        if (lhs.length < rhs.length) {
            for (int i = 0; i < lhs.length; i++) {
                rhs[i] = lhs[i];
            }

            exito = true;
        }

        return exito;
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
    public static void swap(Jugador[] arr, int p1, int p2) {
        Jugador temp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = temp;
    }

    public static void swap(Equipo[] arr, int p1, int p2) {
        Equipo temp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = temp;
    }

    // selection sort
    public static void sortJugadoresPorApellidoNombreSelection(Jugador[] arr) {
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

    public static void sortJugadoresPorApellidoNombreQuick(Jugador[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void mix(Jugador[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = random(0, arr.length - 1);
            swap(arr, i, randomIndex);
        }
    }

    private static void quickSort(Jugador[] arr, int izq, int der) {
        if (izq < der) {
            int pivote = particion(arr, izq, der);
            quickSort(arr, izq, pivote - 1);
            quickSort(arr, pivote + 1, der);
        }
    }

    private static int particion(Jugador[] arr, int izq, int der) {
        Jugador pivote = arr[der];
        int i = izq - 1;
        for (int j = izq; j < der; j++) {
            if (arr[j].compareTo(pivote) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, der);
        return i + 1;
    }

    // selection sort
    public static void sortJugadorPorGoles(Jugador[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int indice_del_menor = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[indice_del_menor].getGoles() < arr[j].getGoles()) {
                    indice_del_menor = j;
                }
            }
            swap(arr, i, indice_del_menor);
        }
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
