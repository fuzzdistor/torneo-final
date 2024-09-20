public class Torneo {
    private Equipo[] m_equipos;
    private Jugador[] m_jugadores;

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

    public Jugador[] getJugadores() {
        return m_jugadores;
    }
}
