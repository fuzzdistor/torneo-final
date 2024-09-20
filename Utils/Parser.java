package Utils;

import Torneo.Equipo;
import Torneo.Jugador;

public class Parser {

    public static Equipo[] parseEquipos(String ruta) {
        String[][] datos = Lector.getContenidos(ruta);
        Equipo[] equipos = new Equipo[datos.length];
        try {
            for (int i = 0; i < datos.length; i++) {
                equipos[i] = new Equipo(datos[i][0], Equipo.Categoria.valueOf(datos[i][1]));
            }
        } catch (IllegalArgumentException ex) {     // Posible error en Equipo.Categoria.valueOf
            System.err.println(ex.getMessage() + "error en la conversión de categoría");
        }

        return equipos;
    }

    public static Jugador[] parseJugadores(String ruta, Equipo[] equipos) {
        String[][] datos = Lector.getContenidos(ruta);
        Jugador[] jugadores = new Jugador[datos.length];

        for (int i = 0; i < datos.length; i++) {
            String apellido = datos[i][0];
            String nombre = datos[i][1];
            int edad = Integer.parseInt(datos[i][2]);
            int dni = Integer.parseInt(datos[i][3]);
            int camiseta = Integer.parseInt(datos[i][4]);

            Equipo equipo = Utils.findEquipo(equipos, datos[i][5]);

            jugadores[i] = new Jugador(apellido, nombre, edad, dni, camiseta, equipo);
            jugadores[i].setEquipo(equipo);
        }

        return jugadores;
    }
}