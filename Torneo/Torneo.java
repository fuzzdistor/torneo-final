package Torneo;

import  Utils.Utils;

public class Torneo {
    private final Equipo[] m_equipos;
    private Jugador[] m_jugadores;
    private Partido[][] m_fechas;

    public static class Partido {
        public Equipo local;
        public Equipo visitante;
        public Jugador[] goleadores_local = new Jugador[20];
        public Jugador[] goleadores_visitante = new Jugador[20];
        public int goles_local;
        public int goles_visitante;
        boolean jugado = false;
    }

    public Torneo(String rutaEquipos, String rutaJugadores) {
        m_equipos = Parser.parseEquipos(rutaEquipos);
        m_jugadores = Parser.parseJugadores(rutaJugadores, m_equipos);
    }

    public boolean addJugadorAEquipo(Jugador jugador, Equipo equipo) {
        boolean ingresado = false;
        boolean ya_existe = Utils.hasJugador(equipo.getJugadores(), jugador);
        if (!ya_existe) {
            ingresado = equipo.addJugador(jugador);
            if (ingresado) {
                // La falta de estructuras dinámicas...
                // tengo dudas sobre si esto me va a dejar arrays sueltos en memoria...
                Jugador[] jugadores = new Jugador[m_jugadores.length + 1];
                Utils.copyArrayEn(m_jugadores, jugadores);
                jugadores[m_jugadores.length] = jugador;
                m_jugadores = jugadores;
            }
        }

        return ingresado;
    }

    public Equipo[] getEquipos() {
        return m_equipos;
    }

    public Partido[][] getFechas() {
        return m_fechas;
    }

    // basado en esta respuesta de stack overflow que me pareció muy creativa https://stackoverflow.com/a/1039500
    public void buildFechas() {
        if (m_equipos.length % 2 == 0)
            buildFechasPar();
        else
            buildFechasImpar();
    }

    private void rotateEquipos(int inicio) {
        Equipo aux = m_equipos[m_equipos.length - 1];
        for (int i = m_equipos.length - 1; i > inicio; i--) {
            m_equipos[i] = m_equipos[i - 1];
        }

        m_equipos[inicio] = aux;
    }

    private void buildFechasImpar() {
        m_fechas = new Partido[m_equipos.length][m_equipos.length / 2];
        for (int i = 0; i < m_fechas.length; i++) {
            for (int j = 0; j < m_fechas[i].length; j++) {
                m_fechas[i][j] = new Partido();
                m_fechas[i][j].local = m_equipos[i * 2];
                m_fechas[i][j].visitante = m_equipos[i * 2 + 1];
            }

            rotateEquipos(0);
        }
    }

    private void buildFechasPar() {
        m_fechas = new Partido[m_equipos.length - 1][m_equipos.length / 2];
        for (int i = 0; i < m_fechas.length; i++) {
            for (int j = 0; j < m_fechas[i].length; j++) {
                m_fechas[i][j] = new Partido();
                m_fechas[i][j].local = m_equipos[j * 2];
                m_fechas[i][j].visitante = m_equipos[j * 2 + 1];
            }

            rotateEquipos(1);
        }

        // doy vuelta el primer partido de la última fecha que el primer equipo juegue de visitante aunque sea una vez
        Partido partido = m_fechas[m_fechas.length - 1][0];
        Equipo aux = partido.local;
        partido.local = partido.visitante;
        partido.visitante = aux;
    }

    public boolean isFechaRangoValido(int fecha) {
        return fecha >= 0 && fecha < m_fechas.length;
    }

    public boolean isFechaCargada(int fecha) {
        return m_fechas[fecha][0].jugado;
    }

    public Jugador[] getJugadores() {
        return m_jugadores;
    }


    /**
     * Calcula la edad promedio de los jugadores del equipo de forma recursiva.
     * @return la edad promedio de los jugadores del equipo o -1 si no hay jugadores registrados.
     */
    public int getEdadPromedio() {
        int resultado = -1;

        // Necesito al menos un jugador para calcular el promedio. Si no hay, salteo la función recursiva y retorno -1.
        if (m_jugadores.length > 0) {
            resultado = getEdadPromedio(0) / m_jugadores.length;
        }

        return resultado;
    }

    private int getEdadPromedio(int indiceJugador) {
        int suma = 0;
        // Si no me pasé de la cantidad de jugadores, sumo la edad del jugador actual y llamo recursivamente al siguiente índice.
        if (indiceJugador < m_jugadores.length) {
            suma = m_jugadores[indiceJugador].getEdad() + getEdadPromedio(indiceJugador + 1);
        }

        return suma;
    }


    /**
     * Busca los jugadores que superan una edad determinada de forma recursiva.
     * @param edad    la edad a superar.
     * @param indice  el índice del jugador actual.
     * @param posNull la posición en la que se debe agregar el jugador al arreglo.
     * @param superan el arreglo donde se guardan los jugadores que superan la edad (ODIO LOS OUT PARAMS 😭).
     */
    private void getJugadoresQueSuperan(int edad, int indice, int posNull, Jugador[] superan) {
        if (indice < m_jugadores.length) {
            // Si la edad del jugador actual supera la edad, lo agrego al arreglo.
            if (m_jugadores[indice].getEdad() > edad) {
                superan[posNull] = m_jugadores[indice];
                getJugadoresQueSuperan(edad, indice+1, posNull + 1, superan);
            } else {
                getJugadoresQueSuperan(edad, indice+1, posNull, superan);
            }
        }
    }
    /**
     * Busca los jugadores que superan la edad promedio por una cantidad de años determinada de forma recursiva.
     * @param anios los años por los que superan la edad promedio.
     */
    public Jugador[] getJugadoresQueSuperanEdadPromedioPor(int anios) {
        int edadPromedio = getEdadPromedio();
        Jugador[] aux = new Jugador[m_jugadores.length];

        // Si no hubo error al calcular la edad promedio, busco los jugadores que superan la edad promedio.
        if (edadPromedio != -1) {
            getJugadoresQueSuperan(edadPromedio + anios, 0, 0, aux);
        }

        return aux;
    }
}
