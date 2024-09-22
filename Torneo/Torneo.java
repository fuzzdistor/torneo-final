package Torneo;

public class Torneo {
    private final Equipo[] m_equipos;
    private final Jugador[] m_jugadores;
    private Partido[][] m_fechas;

    public static class Partido {
        public Equipo local;
        public Equipo visitante;
        public int[] goles;
        public int goles_local;
        public int goles_visitante;
    }

    public Torneo(String rutaEquipos, String rutaJugadores) {
        m_equipos = Parser.parseEquipos(rutaEquipos);
        m_jugadores = Parser.parseJugadores(rutaJugadores, m_equipos);
    }

    public boolean addJugadorAEquipo(Jugador jugador, Equipo equipo) {
        return equipo.addJugador(jugador);
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
        return m_fechas[fecha] != null;
    }

    public void loadFecha() {

    }

    public Jugador[] getJugadores() {
        return m_jugadores;
    }
}
