package Torneo;

import Utils.Lector;
import Utils.Utils;

public class Parser {
    public static String[][] parseContenidos(String contenidos, String separador) {
        String[] lineas = contenidos.split("\n");
        String[][] datos = new String[lineas.length][2];

        for (int i = 0; i < lineas.length; i++) {
            datos[i] = lineas[i].split(separador);
        }
        return datos;
    }

    public static Equipo[] parseEquipos(String ruta) {
        return parseEquipos(ruta, ";");
    }

    public static Equipo[] parseEquipos(String ruta, String separador) {
        String contenidos = Lector.getContenidos(ruta);
        String[][] datos = parseContenidos(contenidos, separador);

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
        return parseJugadores(ruta, ";", equipos);
    }

    public static Jugador[] parseJugadores(String ruta, String separador, Equipo[] equipos) {
        String contenidos = Lector.getContenidos(ruta);
        String[][] datos = parseContenidos(contenidos, separador);
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
