package Torneo;

import java.util.Scanner;

import Utils.Utils;

public class UI {
    enum Opcion {
        ArmarFixture            (1),
        AgregarJugador          (2),
        IngresarResultadosFecha (3),
        VerResultadosFecha      (4),
        VerTablaPosiciones      (5),
        VerTablaGoleadores      (6),
        VerJugadores            (7),
        VerEstadisticas         (8),
        Salir                   (0);

        private final int value;

        Opcion(int value) {
            this.value = value;
        }

        private int getValue() {
            return value;
        }

        public static Opcion fromInt(int value) {
            for (Opcion opcion : Opcion.values()) {
                if (opcion.getValue() == value) {
                    return opcion;
                }
            }
            throw new IllegalArgumentException("Se ingresó un número inválido: " + value);
        }
    }

    enum OpcionesEstadisticas {
        VerEdadPromedio,
        VerCantidadSuperaEdadPromedio,
        VerPrimerJugadorQueTengaMenosDeEdad,
        Salir,
    }

    public static void printTablaPosiciones(Torneo torneo) {
        System.out.printf("%-20s %3s %3s %3s %3s %3s %3s %3s %3s%n", "Equipo", "Pts", "PJ", "PG", "PE", "PP", "GF", "GC", "DF");
        Equipo[] equipos = torneo.getEquipos();
        Utils.selectionSort(equipos);

        for (int i = equipos.length - 1; i >= 0; i--) {
            System.out.printf("%-20s %3d %3d %3d %3d %3d %3d %3d %3d%n", equipos[i].getNombre(), equipos[i].getPuntos(), equipos[i].getPartidosJugados(), equipos[i].getGanados(), equipos[i].getEmpatados(), equipos[i].getPerdidos(), equipos[i].getGolesAFavor(), equipos[i].getGolesEnContra(), equipos[i].getDiferenciaGoles());
        }
    }

    public static void run(Torneo torneo) {
        boolean abierto = true;
        while (abierto) {
            Opcion eleccion = pickOpciones();

            switch (eleccion) {
				case AgregarJugador:
                    addJugador(torneo);
					break;

				case ArmarFixture:
                    torneo.createFechas();
                    System.out.println("");
					break;

				case IngresarResultadosFecha:
                    inputResultadosFecha(torneo);
					break;

				case VerEstadisticas:
                    {
                        OpcionesEstadisticas eleccionEstadistica = pickOpcionesEstadistica();
                        switch (eleccionEstadistica)
                        {
							case VerCantidadSuperaEdadPromedio:
                                printSuperaEdadPromedio(torneo);
								break;
							case VerEdadPromedio:
                                printEdadPromedio(torneo);
								break;
							case VerPrimerJugadorQueTengaMenosDeEdad:
                                printJugadorMenorEdad(torneo);
								break;
							case Salir:
								break;
							default:
								break;
                        }
                    }
					break;

				case VerJugadoresEficaz:
                    printJugadoresEficaz(torneo);
					break;

				case VerJugadoresFuerzaBruta:
                    printJugadoresFuerzaBruta(torneo);
					break;

				case VerResultadosFecha:
                    {
                        int indiceFecha = pickFecha();
                        printFecha(indice);
                    }
					break;

				case VerTablaGoleadores:
                    printTablaGoleadores(torneo);
					break;

				case VerTablaPosiciones:
                    printTablaPosiciones(torneo);
					break;

				case Salir:
                    System.out.println("¡Hasta la próxima!");
                    abierto = false;
					break;

				default:
					break;
            }
        }
    }

    private static Opcion pickOpciones() {
        Scanner sc = new java.util.Scanner(System.in);
        Opcion opcion = null;

        while (opcion == null)
        {
            System.out.printf("Elija una opción:%n%s%n%s%n%s%n%s%n", 
                    "1) Armar Fixture", 
                    "2) Agregar un nuevo jugador a un equipo", 
                    "3) Ingresar los resultados de una fecha", 
                    "4) Ver resultados de una fecha",
                    "5) Ver tabla de posiciones",
                    "6) Ver tabla de goleadores",
                    "7) Ver lista de jugadores ->",
                    "8) Ver estadisticas ->",
                    "0) Salir"
                    );

            int entrada = sc.nextInt();

            try {
                opcion = Opcion.fromInt(entrada);
            } catch (IllegalArgumentException ex)
            {
                System.out.println(ex.getMessage());
            }
        }

        sc.close();
        return opcion;
    }

    public static void addJugador(Torneo torneo) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el apellido del jugador:");
        String apellido = sc.nextLine();

        System.out.println("Ingrese el nombre del jugador:");
        String nombre = sc.nextLine();

        System.out.println("Ingrese la edad del jugador:");
        int edad = sc.nextInt();

        System.out.println("Ingrese el dni del jugador:");
        int dni = sc.nextInt();

        System.out.println("Ingrese el equipo al que pertenece el jugador:");
        String nombre_equipo = sc.nextLine();

        sc.close();

        Jugador jugador = new Jugador(apellido, nombre, edad, dni);

        Equipo equipo = Utils.findEquipo(torneo.getEquipos, nombre_equipo);

        if (equipo == null) {
            System.out.println("No se encontró el equipo.");
        } else {
            torneo.addJugadorAEquipo(jugador, equipo);
        }
    }

    public static void buildFechas(Torneo torneo) {
        System.out.println("Armando fixture...");
        torneo.createFechas();
        System.out.println("Fixture armado:");
        printFechas(torneo);
    }

    public static void inputResultadosFecha(Torneo torneo) {
        Scanner sc = new Scanner(System.in);

        // Si no hay fechas armadas, ofrezco la opción de armarlas antes de ingresar resultados
        if (torneo.getFechas() == null) {
            System.out.println("No hay fechas armadas. ¿Desea armar el fixture? [SI/no]");
            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("no")) {
                System.out.println("No se puede ingresar resultados sin haber armado las fechas.");
                return;
            }

            buildFechas(torneo);
        }


        // Pido el número de la fecha
        System.out.println("Ingrese el número de la fecha:");
        int fecha = sc.nextInt();
        while (!torneo.isFechaRangoValido(fecha - 1)) {
            System.out.println("La fecha ingresada no es válida. Ingrese un número entre 1 y " + torneo.getFechas().length + " (o 0 para abortar la operación).");
            System.out.println("Ingrese el número de la fecha:");
            fecha = sc.nextInt();
            if (fecha == 0) {
                System.out.println("Operación cancelada.");
                return;
            }
        }

        // No se debe poder ingresar los datos de una fecha más de una vez. Si ya tiene datos cargados retorno.
        if (torneo.isFechaCargada(fecha - 1)) {
            System.out.println("Esa fecha ya tiene resultados cargados!");
            return;
        }

        Torneo.Partido[] partidos = torneo.getFechas()[fecha - 1];

        for (int i = 0; i < partidos.length; i++) {
            Torneo.Partido partido = partidos[i];
            System.out.printf("Partido nro %d: %s vs %s%n", i + 1, partido.local.getNombre(), partido.visitante.getNombre());
            System.out.println("Ingrese las camisetas que corresponden a todos los goles (0 para finalizar):");
            int camiseta = sc.nextInt();
            while (camiseta != 0)
            {
                // busco el jugador en ambos equipos
                Jugador jugador = partido.local.findJugador(camiseta);
                if (jugador != null) {
                    partido.local.addGol(jugador);
                    partido.goles_local++;
                } else {
                    jugador = partido.visitante.findJugador(camiseta);
                    if (jugador != null) {
                        partido.visitante.addGol(jugador);
                        partido.goles_visitante++;
                    } else {
                        System.out.println("No se encontró el jugador con la camiseta ingresada (" + camiseta + ").");
                    }
                }

                camiseta = sc.nextInt();
            }

            if (partido.goles_local > partido.goles_visitante) {
                partido.local.addPartidoGanado();
                partido.visitante.addPartidoPerdido();
            } else if (goles_local < goles_visitante) {
                partido.local.addPartidoPerdido();
                partido.visitante.addPartidoGanado();
            } else {
                partido.local.addPartidoEmpatado();
                partido.visitante.addPartidoEmpatado();
            }

            partidos[i].local.addGolesAFavor(goles_local);
            partidos[i].local.addGolesEnContra(goles_visitante);
            partidos[i].visitante.addGolesAFavor(goles_visitante);
            partidos[i].visitante.addGolesEnContra(goles_local);
        }

    }
                
    public static void printFechas(Torneo torneo) {
        Torneo.Partido[][] fechas = torneo.getFechas();
        if (fechas == null) {
            System.out.println("No hay fechas disponibles. Considere cargarlas primero.");
            return;
        }

        // me fijo cuántas fechas hay cargadas
        int cargados = Utils.countNoNulos(fechas);

        for (int i = 0; i < cargados; i++) {
            System.out.println("Fecha " + (i + 1));
            for (int j = 0; j < torneo.getFechas()[i].length; j++) {
                Torneo.Partido partido = fechas[i][j];
                System.out.printf("%-20svs%20s%n", partido.local.getNombre(), partido.visitante.getNombre());
            }
        }
        for (int i = cargados; i < fechas.length; i++) {
            System.out.println("Fecha " + (i + 1) + " no está cargada aún.");
        }
    }

    public static void printFecha(Torneo torneo, int fecha) {
        Torneo.Partido[][] fechas = torneo.getFechas();
        if (fechas == null) {
            System.out.println("No hay fechas disponibles. Considere cargarlas primero.");
            return;
        }

        // checkeo si la fecha está en un rango válido y si está cargada
        if (!torneo.isFechaRangoValido(fecha - 1)) {
            System.out.println("La fecha no es válida. Ingrese un número entre 1 y " + fechas.length + ".");
            return;

        } else if (!torneo.isFechaCargada(fecha - 1)) {
            System.out.println("No hay partidos cargados para la fecha " + fecha + ". Considere cargarlos primero.");
            return;
        }

        System.out.println("Fecha " + fecha);
        for (int j = 0; j < fechas[fecha - 1].length; j++) {
            Torneo.Partido partido = fechas[fecha - 1][j];
            System.out.printf("%-20svs%20s%n", partido.local.getNombre(), partido.visitante.getNombre());
        }
    }
}
