package Torneo;

import java.time.Instant;
import java.util.Scanner;

import Utils.Utils;

public class UI {
    enum Opcion {
        ArmarFixture(1),
        AgregarJugador(2),
        IngresarResultadosFecha(3),
        VerResultadosFecha(4),
        VerTablaPosiciones(5),
        VerTablaGoleadores(6),
        VerJugadores(7),
        VerEstadisticas(8),
        Salir(0);

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
        VerEdadPromedio(1),
        VerCantidadSuperaEdadPromedio(2),
        VerPrimerJugadorQueTengaMenosDeEdad(3),
        Salir(0);

        private final int value;

        OpcionesEstadisticas(int value) {
            this.value = value;
        }

        private int getValue() {
            return value;
        }

        public static OpcionesEstadisticas fromInt(int value) {
            for (OpcionesEstadisticas opcion : OpcionesEstadisticas.values()) {
                if (opcion.getValue() == value) {
                    return opcion;
                }
            }
            throw new IllegalArgumentException("Se ingresó un número inválido: " + value);
        }
    }

    final private Scanner m_sc;
    final private Torneo m_torneo;

    public UI(Torneo torneo) {
        m_torneo = torneo;
        m_sc = new Scanner(System.in);
    }

    public void run() {
        boolean abierto = true;
        while (abierto) {
            // elijo una opción
            switch (pickOpciones()) {
                case AgregarJugador:
                    addJugador();
                    break;

                case ArmarFixture:
                    buildFechas();
                    break;

                case IngresarResultadosFecha:
                    inputResultadosFecha();
                    break;

                case VerEstadisticas: {
                    // elijo una opción de estadísticas
                    switch (pickOpcionesEstadistica()) {
                        case VerCantidadSuperaEdadPromedio:
                            printSuperaEdadPromedio(5);
                            break;
                        case VerEdadPromedio:
                            printEdadPromedio();
                            break;
                        case VerPrimerJugadorQueTengaMenosDeEdad: {
                            Equipo eq = pickEquipo();

                            System.out.println("Ingrese la edad máxima:");
                            while (!m_sc.hasNextInt()) {
                                System.out.println("Ingrese un número válido.");
                                m_sc.next();
                            }
                            int edad = m_sc.nextInt();

                            printJugadorMenorEdad(eq, edad);
                        }
                            break;
                        case Salir:
                            break;
                        default:
                            break;
                    }
                }
                break;

                case VerJugadores:
                    printJugadoresPorNombre();
                    break;

                case VerResultadosFecha: {
                    int indiceFecha = pickFecha();
                    printResultadosFecha(indiceFecha);
                }
                break;

                case VerTablaGoleadores:
                    printTablaGoleadores();
                    break;

                case VerTablaPosiciones:
                    printTablaPosiciones();
                    break;

                case Salir:
                    close();
                    abierto = false;
                    break;

                default:
                    break;
            }
        }
    }

    private Equipo pickEquipo() {
        Equipo eq = null;
        // TODO averiguar porqué tengo que purgar la linea
        m_sc.nextLine();
        while (eq == null) {
            System.out.println("Ingrese el nombre del equipo:");
            String nombreEquipo = m_sc.nextLine();
            eq = Utils.findEquipo(m_torneo.getEquipos(), nombreEquipo);
            if (eq == null) {
                System.out.println("No se encontró el equipo. Vuelva a intentarlo.");
            }
        }
        return eq;
    }

    private void printResultadosFecha(int numeroFecha) {
        Torneo.Partido[][] fechas = m_torneo.getFechas();
        if (fechas == null) {
            System.out.println("No hay fechas disponibles. Considere cargarlas primero.");
            return;
        }

        if (!m_torneo.isFechaRangoValido(numeroFecha - 1)) {
            System.out.println("La fecha no es válida. Ingrese un número entre 1 y " + fechas.length + ".");
            return;
        }

        if (!m_torneo.isFechaCargada(numeroFecha - 1)) {
            System.out.println("No hay partidos cargados para la fecha " + numeroFecha + ". Considere cargarlos primero.");
            return;
        }

        System.out.println("Fecha " + (numeroFecha));
        for (int j = 0; j < m_torneo.getFechas()[numeroFecha - 1].length; j++) {
            Torneo.Partido partido = fechas[numeroFecha - 1][j];
            System.out.printf("%-2d %-20svs%20s %2d%n", partido.goles_local, partido.local.getNombre(), partido.visitante.getNombre(), partido.goles_visitante);
        }
        System.out.println();
    }

    private void close() {
        System.out.println("¡Hasta la próxima!");
        m_sc.close();
    }

    private void printTablaPosiciones() {
        System.out.printf("%-20s %3s %3s %3s %3s %3s %3s %3s %3s%n", "Equipo", "Pts", "PJ", "PG", "PE", "PP", "GF", "GC", "DF");
        Equipo[] equipos = m_torneo.getEquipos();
        Utils.selectionSort(equipos);

        for (int i = equipos.length - 1; i >= 0; i--) {
            System.out.printf("%-20s %3d %3d %3d %3d %3d %3d %3d %3d%n", equipos[i].getNombre(), equipos[i].getPuntos(), equipos[i].getPartidosJugados(), equipos[i].getGanados(), equipos[i].getEmpatados(), equipos[i].getPerdidos(), equipos[i].getGolesAFavor(), equipos[i].getGolesEnContra(), equipos[i].getDiferenciaGoles());
        }
    }

    private void printJugadoresPorNombre() {
        Jugador[] jugadores = m_torneo.getJugadores();

        Utils.mix(jugadores);
        System.out.println("Se mezcló la lista de jugadores.");

        int metodo = pickMetodoOrdenamiento();

        Instant tiempoInicio = Instant.now();
        Instant tiempoFin = Instant.now();

        switch (metodo) {
            case 1:
                Utils.sortJugadoresPorApellidoNombreSelection(jugadores);
                tiempoFin = Instant.now();
                break;
            case 2:
                Utils.sortJugadoresPorApellidoNombreQuick(jugadores);
                tiempoFin = Instant.now();
                break;
            default:
                break;
        }
        System.out.println("Lista de jugadores:\n");

        for (int i = 0; i < jugadores.length; i++) {
            System.out.printf("%s, %s%n", jugadores[i].getApellido(), jugadores[i].getNombre());
        }

        System.out.printf("%nTiempo que tomó el ordenamiento: %dns.", tiempoFin.getNano() - tiempoInicio.getNano());
    }

    private void printTablaGoleadores() {
        Jugador[] jugadores = m_torneo.getJugadores();
        Utils.sortJugadorPorGoles(jugadores);
        System.out.println("Tabla de goleadores:");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.printf("%-20s %d goles%n", jugadores[i].toString(), jugadores[i].getGoles());
        }
    }

    private int pickFecha() {
        System.out.println("Ingrese el número de la fecha:");

        while (!m_sc.hasNextInt()) {
            System.out.println("Ingrese un número válido.");
            m_sc.next();
        }

        return m_sc.nextInt();
    }

    private int pickMetodoOrdenamiento() {
        System.out.println("Elija un método de ordenamiento:");
        System.out.println("1) SelectionSort");
        System.out.println("2) QuickSort");
        int metodo = -1;
        while (metodo < 1 || metodo > 2) {
            while (!m_sc.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                m_sc.next();
            }
            metodo = m_sc.nextInt();
        }
        return metodo;
    }

    private void printJugadorMenorEdad(Equipo equipo, int edad) {
        Jugador jugador = equipo.getPrimerJugadorMenorA(edad);
        if (jugador != null) {
            System.out.printf("El primer jugador registrado del equipo %s que tiene menos de %d años es: %s %s con %d años%n",
                    equipo.getNombre(),
                    edad,
                    jugador.getApellido(),
                    jugador.getNombre(),
                    jugador.getEdad());
        } else {
            System.out.printf("No se encontró ningún jugador en el equipo %s que tenga menos de %d años.%n", equipo.getNombre(), edad);
        }
    }

    private void printEdadPromedio() {
        System.out.printf("La edad promedio de los jugadores es: %2d%n", m_torneo.getEdadPromedio());
    }

    private void printSuperaEdadPromedio(int anios) {
        Jugador[] jugadoresQueSuperan = m_torneo.getJugadoresQueSuperanEdadPromedioPor(anios);
        int cantidad = Utils.countNoNulos(jugadoresQueSuperan);
        System.out.printf("La cantidad de jugadores que superan la edad promedio por %d años es: %d%n", anios, cantidad);
    }

    private OpcionesEstadisticas pickOpcionesEstadistica() {
        OpcionesEstadisticas opcion = null;
        System.out.println("""
                    Elija una opción:
                    1) Ver la edad promedio
                    2) Ver la cantidad de jugadores que superan la edad promedio por 5 años
                    3) Ver el primer jugador un equipo que tenga menos de una edad dada
                    0) Salir"""
        );
        while (opcion == null) {
            while (!m_sc.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                m_sc.next();
            }

            int entrada = m_sc.nextInt();
            try {
                opcion = OpcionesEstadisticas.fromInt(entrada);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return opcion;
    }
//TODO ARREGLAR GOLES A FAVOR QUE CREO QUE SE ESTAN REGISTRANDO DOS VECES
    private Opcion pickOpciones() {
        Opcion opcion = null;

        while (opcion == null) {
            System.out.println("""
                    
                    Elija una opción:
                    1) Armar Fixture
                    2) Agregar un nuevo jugador a un equipo
                    3) Ingresar los resultados de una fecha
                    4) Ver resultados de una fecha
                    5) Ver tabla de posiciones
                    6) Ver tabla de goleadores
                    7) Ver lista de jugadores ->
                    8) Ver estadisticas ->
                    0) Salir"""
            );

            while (!m_sc.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                m_sc.next();
            }

            int entrada = m_sc.nextInt();

            try {
                opcion = Opcion.fromInt(entrada);
            } catch (IllegalArgumentException ex)
            {
                System.out.println(ex.getMessage());
            }
        }

        return opcion;
    }

    private void addJugador() {
        // TODO: averiguar porqué tengo que purgear la linea
        m_sc.nextLine();

        System.out.println("Ingrese el apellido del jugador:");
        final String apellido = m_sc.nextLine();

        System.out.println("Ingrese el nombre del jugador:");
        final String nombre = m_sc.nextLine();

        System.out.println("Ingrese la edad del jugador:");
        final int edad = m_sc.nextInt();

        System.out.println("Ingrese el dni del jugador:");
        final int dni = m_sc.nextInt();

        System.out.println("Ingrese la camiseta del jugador:");
        final int camiseta = m_sc.nextInt();

        // TODO: averiguar porqué tengo que purgear la linea
        m_sc.nextLine();
        System.out.println("Ingrese el equipo al que pertenece el jugador:");
        final String nombre_equipo = m_sc.nextLine();

        Jugador jugador = new Jugador(apellido, nombre, edad, dni, camiseta);

        Equipo equipo = Utils.findEquipo(m_torneo.getEquipos(), nombre_equipo);

        if (equipo == null) {
            System.out.println("No se encontró el equipo.");
        } else {
            if (m_torneo.addJugadorAEquipo(jugador, equipo)) {
                System.out.println("Se agregó el jugador al equipo.");
            } else {
                System.out.println("No se pudo agregar el jugador al equipo o ya existía en este torneo.");
            }
        }
    }

    private void buildFechas() {
        System.out.println("Armando fixture...");
        m_torneo.buildFechas();
        System.out.println("Fixture armado:");
        printFechas();
    }

    private void printJugadoresCamiseta(Jugador[] jugadores) {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] != null) {
                System.out.printf("%s %s (%d)%n", jugadores[i].getApellido(), jugadores[i].getNombre(), jugadores[i].getCamiseta());
            }
        }
    }

    // TODO: refactorizar delegando la carga de los datos a torneo
    private void inputResultadosFecha() {

        // Si no hay fechas armadas, ofrezco la opción de armarlas antes de ingresar resultados
        if (m_torneo.getFechas() == null) {
            System.out.println("No hay fechas armadas. ¿Desea armar el fixture? [SI/no]");
            // TODO: averiguar porqué tengo que purgar la linea
            m_sc.nextLine();
            String respuesta = m_sc.nextLine();

            if (respuesta.equalsIgnoreCase("no")) {
                System.out.println("No se puede ingresar resultados sin haber armado las fechas.");
                return;
            }

            buildFechas();
        }


        // Pido el número de la fecha
        System.out.println("Ingrese el número de la fecha:");
        int fecha = m_sc.nextInt();
        while (!m_torneo.isFechaRangoValido(fecha - 1)) {
            System.out.println("La fecha ingresada no es válida. Ingrese un número entre 1 y " + m_torneo.getFechas().length + " (o 0 para abortar la operación).");
            System.out.println("Ingrese el número de la fecha:");
            fecha = m_sc.nextInt();
            if (fecha == 0) {
                System.out.println("Operación cancelada.");
                return;
            }
        }

        // No se debe poder ingresar los datos de una fecha más de una vez. Si ya tiene datos cargados retorno.
        if (m_torneo.isFechaCargada(fecha - 1)) {
            System.out.println("Esa fecha ya tiene resultados cargados!");
            return;
        }

        Torneo.Partido[] partidos = m_torneo.getFechas()[fecha - 1];

        for (int i = 0; i < partidos.length; i++) {
            Torneo.Partido partido = partidos[i];
            System.out.printf("Partido nro %d: %s vs %s%n", i + 1, partido.local.getNombre(), partido.visitante.getNombre());

            // imprimo los jugadores
            Jugador[] jugadoresLocales = partido.local.getJugadores();
            Jugador[] jugadoresVisitantes = partido.visitante.getJugadores();
            System.out.printf("Jugadores locales (%s):%n", partido.local.getNombre());
            printJugadoresCamiseta(jugadoresLocales);

            System.out.println("Ingrese los números de las camisetas que corresponden a todos los goles LOCALES (0 para finalizar):");
            {
                int indiceGoleador = 0;
                int camiseta = m_sc.nextInt();
                while (camiseta != 0) {
                    // busco el jugador en ambos equipos
                    Jugador jugador = partido.local.findJugador(camiseta);
                    if (jugador != null) {
                        partido.local.addGol(jugador);
                        partido.goles_local++;
                        partido.goleadores_local[indiceGoleador] = jugador;
                        indiceGoleador++;
                    } else {
                        System.out.println("La camiseta ingresada no corresponde a ningún jugador en este partido y fue ignorada.");
                    }
                    camiseta = m_sc.nextInt();
                }
            }

            System.out.printf("Jugadores visitantes (%s):%n", partido.visitante.getNombre());
            printJugadoresCamiseta(jugadoresVisitantes);

            System.out.println("Ingrese los números de las camisetas que corresponden a todos los goles VISITANTES (0 para finalizar):");
            {
                int indiceGoleador = 0;
                int camiseta = m_sc.nextInt();
                while (camiseta != 0) {
                    // busco el jugador en ambos equipos
                    Jugador jugador = partido.visitante.findJugador(camiseta);
                    if (jugador != null) {
                        partido.visitante.addGol(jugador);
                        partido.goles_visitante++;
                        partido.goleadores_visitante[indiceGoleador] = jugador;
                        indiceGoleador++;
                    } else {
                        System.out.println("La camiseta ingresada no corresponde a ningún jugador en este partido y fue ignorada.");
                    }
                    camiseta = m_sc.nextInt();
                }
            }

            if (partido.goles_local > partido.goles_visitante) {
                partido.local.addPartidoGanado();
                partido.visitante.addPartidoPerdido();
            } else if (partido.goles_local < partido.goles_visitante) {
                partido.local.addPartidoPerdido();
                partido.visitante.addPartidoGanado();
            } else {
                partido.local.addPartidoEmpatado();
                partido.visitante.addPartidoEmpatado();
            }

            partido.local.addGolesAFavor(partido.goles_local);
            partido.local.addGolesEnContra(partido.goles_visitante);
            partido.visitante.addGolesAFavor(partido.goles_visitante);
            partido.visitante.addGolesEnContra(partido.goles_local);
            partido.jugado = true;
        }
    }

    private void printFechas() {
        Torneo.Partido[][] fechas = m_torneo.getFechas();
        if (fechas == null) {
            System.out.println("No hay fechas disponibles. Considere cargarlas primero.");
            return;
        }

        // me fijo cuántas fechas hay cargadas
        int cargados = Utils.countNoNulos(fechas);

        for (int i = 0; i < cargados; i++) {
            System.out.println("Fecha " + (i + 1));
            for (int j = 0; j < m_torneo.getFechas()[i].length; j++) {
                Torneo.Partido partido = fechas[i][j];
                System.out.printf("%-20svs%20s%n", partido.local.getNombre(), partido.visitante.getNombre());
            }
            System.out.println();
        }
        for (int i = cargados; i < fechas.length; i++) {
            System.out.println("Fecha " + (i + 1) + " no está cargada aún.");
        }
    }
}
