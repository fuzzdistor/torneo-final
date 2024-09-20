package Utils;

import Torneo.Equipo;
import Torneo.Jugador;

public class Utils {
    /**
     * Cuenta la cantidad de equipos no nulos en un array de jugadores.
     * @param equipos Array de equipos. Asume que no es nulo.
     * @return Cantidad de equipos no nulos.
     * @implNote Asume que no hay espacios vacíos entre elementos.
     */
    public static int countNoNulos(Equipo[] equipos) {
        int count = 0;
        if (equipos.length > 0)
            while (equipos[count] != null)
                count++;

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
        if (jugadores.length > 0)
            while (jugadores[count] != null)
                count++;

        return count;
    }

    public static Equipo findEquipo(Equipo[] equipos, String nombre) {
        Equipo equipo = null;
        for (int j = 0; j < equipos.length; j++) {
            if (equipos[j].getNombre().equalsIgnoreCase(nombre)) {
                equipo = equipos[j];
                break;
            }
        }

        return equipo;
    }
}
