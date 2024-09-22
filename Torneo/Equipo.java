package Torneo;

import Utils.Utils;

public class Equipo {
    // Me dí la libertad de usar enums para la categoría porque me gustan más que usar un char.
    public enum Categoria {
        A,
        B,
        C,
        D,
        E,
        F
    }

    final private String m_nombre;
    final private Categoria m_categoria;
    final private Jugador[] m_jugadores;
    private int m_ganados;
    private int m_perdidos;
    private int m_empatados;
    private int m_golesFavor;
    private int m_golesContra;

    /**
     * Constructor de la clase Equipo.
     * @param nombre    el nombre del equipo.
     * @param categoria la categoría del equipo.
     * @implNote el arreglo de jugadores se inicializa con 26 espacios, que es una buena cantidad máxima de jugadores que puede tener un equipo en un torneo.
     */
    public Equipo(String nombre, Categoria categoria) {
        m_nombre = nombre;
        m_categoria = categoria;
        m_jugadores = new Jugador[26];
        m_ganados = 0;
        m_perdidos = 0;
        m_empatados = 0;
        m_golesFavor = 0;
        m_golesContra = 0;
    }

    /**
     * Calcula la cantidad de partidos jugados por el equipo.
     * @return la cantidad de partidos jugados por el equipo.
     */
    public int getPartidosJugados() {
        return m_perdidos + m_empatados + m_ganados;
    }

    /**
     * Calcula la diferencia de goles del equipo. La diferencia de goles es la resta de los goles a favor menos los goles en contra.
     * @return la diferencia de goles del equipo.
     */
    public int getDiferenciaGoles() {
        return m_golesFavor - m_golesContra;
    }

    /**
     * Calcula la cantidad de puntos que tiene el equipo.
     * Los partidos ganados suman 3 puntos, los empatados 1 y los perdidos 0.
     * @return los puntos del equipo.
     */
    public int getPuntos() {
        return m_ganados * 3 + m_empatados;
    }

    /**
     * Calcula la edad promedio de los jugadores del equipo de forma recursiva.
     * @return la edad promedio de los jugadores del equipo o -1 si no hay jugadores registrados.
     */
    public int getEdadPromedio() {
        int resultado = -1;
        int cantidadJugadores = getCantidadJugadores();

        // Necesito al menos un jugador para calcular el promedio. Si no hay, salteo la función recursiva y retorno -1.
        if (cantidadJugadores > 0) {
            resultado = getEdadPromedio(0, cantidadJugadores) / cantidadJugadores;
        }

        return resultado;
    }

    private int getEdadPromedio(int indiceJugador, int cantidadJugadores) {
        int suma = 0;
        // Si no me pasé de la cantidad de jugadores, sumo la edad del jugador actual y llamo recursivamente al siguiente índice.
        if (indiceJugador < cantidadJugadores) {
            suma = m_jugadores[indiceJugador].getEdad() + getEdadPromedio(indiceJugador + 1, cantidadJugadores);
        }

        return suma;
    }

    /**
     * Busca los jugadores que superan la edad promedio por una cantidad de años determinada de forma recursiva.
     *
     * @param anios los años por los que superan la edad promedio.
     */
    public Jugador[] getJugadoresQueSuperanEdadPromedioPor(int anios) {
        int edadPromedio = getEdadPromedio();
        Jugador[] aux = new Jugador[getCantidadJugadores()];

        // Si no hubo error al calcular la edad promedio, busco los jugadores que superan la edad promedio.
        if (edadPromedio != -1) {
            getJugadoresQueSuperan(edadPromedio + anios, 0, 0, aux);
        }

        Jugador[] jugadoresQueSuperan = new Jugador[Utils.countNoNulos(aux)];

        for (int i = 0; i < jugadoresQueSuperan.length; i++) {
            jugadoresQueSuperan[i] = aux[i];
        }

        return jugadoresQueSuperan;
    }

    /**
     * Busca los jugadores que superan una edad determinada de forma recursiva.
     * @param edad    la edad a superar.
     * @param indice  el índice del jugador actual.
     * @param posNull la posición en la que se debe agregar el jugador al arreglo.
     * @param superan el arreglo donde se guardan los jugadores que superan la edad (ODIO LOS OUT PARAMS 😭).
     */
    private void getJugadoresQueSuperan(int edad, int indice, int posNull, Jugador[] superan) {
        if (indice < getCantidadJugadores()) {
            // Si la edad del jugador actual supera la edad, lo agrego al arreglo.
            if (m_jugadores[indice].getEdad() > edad) {
                superan[posNull] = m_jugadores[indice];
                getJugadoresQueSuperan(edad, indice + 1, posNull + 1, superan);
            } else {
                getJugadoresQueSuperan(edad, indice + 1, posNull, superan);
            }
        }
    }

    /**
     * Busca el primer jugador que sea menor a una edad determinada de forma recursiva.
     * Si no hay jugadores menores a la edad dada devuelve null.
     * @param edad la edad tope.
     * @return el primer jugador menor a la edad o null si no encuentra.
     */
    public Jugador getPrimerJugadorMenorA(int edad) {
        return getPrimerJugadorMenorA(edad, 0, getCantidadJugadores());
    }

    private Jugador getPrimerJugadorMenorA(int edad, int index, int cantidadJugadores) {
        Jugador jugador = null;

        // Si no me pasé de la cantidad de jugadores, chequeo si el jugador actual es menor a la edad dada.
        // Si no, llamo recursivamente al siguiente índice.
        if (index < cantidadJugadores) {
            if (m_jugadores[index].getEdad() < edad) {
                jugador = m_jugadores[index];
            } else {
                jugador = getPrimerJugadorMenorA(edad, index + 1, cantidadJugadores);
            }
        }

        return jugador;
    }

    /**
     * Devuelve el nombre del equipo.
     * @return el nombre del equipo.
     */
    public String getNombre() {
        return m_nombre;
    }

    /**
     * Devuelve la categoría del equipo.
     * @return la categoría del equipo.
     */
    public Categoria getCategoria() {
        return m_categoria;
    }

    /**
     * Devuelve los jugadores del equipo.
     * @return los jugadores del equipo.
     */
    public Jugador[] getJugadores() {
        return m_jugadores;
    }

    /**
     * Devuelve la cantidad de partidos ganados por el equipo.
     * @return la cantidad de partidos ganados por el equipo.
     */
    public int getGanados() {
        return m_ganados;
    }

    /**
     * Devuelve la cantidad de partidos perdidos por el equipo.
     * @return la cantidad de partidos perdidos por el equipo.
     */
    public int getPerdidos() {
        return m_perdidos;
    }

    /**
     * Devuelve la cantidad de partidos empatados por el equipo.
     * @return la cantidad de partidos empatados por el equipo.
     */
    public int getEmpatados() {
        return m_empatados;
    }

    /**
     * Devuelve la cantidad de goles a favor del equipo.
     * @return la cantidad de goles a favor del equipo.
     */
    public int getGolesAFavor() {
        return m_golesFavor;
    }

    /**
     * Devuelve la cantidad de goles en contra del equipo.
     * @return la cantidad de goles en contra del equipo.
     */
    public int getGolesEnContra() {
        return m_golesContra;
    }

    /**
     * Chequea la cantidad de miembros del equipo. Asume que no hay espacios vacíos entre elementos.
     * @return la cantidad de miembros del equipo.
     */
    public int getCantidadJugadores() {
        return Utils.countNoNulos(m_jugadores);
    }

    /**
     * Devuelve una cadena con el nombre y la categoría del equipo.
     * @return el nombre y la categoría del equipo separados por un espacio.
     */
    public String toString() {
        return m_nombre + ' ' + m_categoria;
    }

    /**
     * Agrega un jugador al equipo si hay lugar y le asigna el equipo. Devuelve falso si no hay lugar.
     *
     * @param jugador el jugador a agregar.
     * @return true si se pudo agregar el jugador, false si no hay espacio.
     */
    public boolean addJugador(Jugador jugador) {
        //TODO: refactorear con la garantia de que el arreglo es compacto
        int cantidad_de_miembros = getCantidadJugadores();

        // Si hay menos miembros que espacios en el arreglo, entra un jugador más.
        boolean hay_espacio = cantidad_de_miembros < m_jugadores.length;

        if (hay_espacio) {
            m_jugadores[cantidad_de_miembros] = jugador;
        }

        return hay_espacio;
    }

    /**
     * Agrega un partido jugado al equipo.
     *
     * @param golesAFavor   los goles a favor del equipo.
     * @param golesEnContra los goles en contra del equipo.
     */
    public void addPartido(int golesAFavor, int golesEnContra) {
        m_golesFavor += golesAFavor;
        m_golesContra += golesEnContra;

        if (golesAFavor > golesEnContra) {
            m_ganados++;
        } else if (golesAFavor < golesEnContra) {
            m_perdidos++;
        } else {
            m_empatados++;
        }
    }


    /**
     * Compara dos equipos por puntos o, en caso de un empate, por diferencia de goles.
     * @param otro el equipo con el que comparar.
     * @return número negativo si este equipo resulta menor, 0 si tienen la misma cantidad de puntos y diferencia de goles,
     * y un número positivo si este equipo resulta mayor.
     */
    public int compareTo(Equipo otro) {
        int resultado = getPuntos() - otro.getPuntos();
        if (resultado == 0) {
            if (getDiferenciaGoles() != otro.getDiferenciaGoles()) {
                resultado = getDiferenciaGoles() - otro.getDiferenciaGoles();
            }
        }

        return resultado;
    }

    /**
     * Busca al jugador con número de camiseta igual al dado por parámetro y devuelve el índice en el que se encuentra
     * o -1 si no lo encontró.
     * @param camiseta número de la camiseta que se busca.
     * @return índice del jugador al que le corresponde la camiseta o -1 si no fue encontrado.
    */
    public int indexOfJugador(int camiseta) {
        int i = 0;
        while (i < m_jugadores.length) {
            if (m_jugadores[i] != null && m_jugadores[i].getCamiseta() == camiseta)
                break;
            i++;
        }

        // si me pasé del rango es porque no encontré al jugador. Devuelvo -1
        if (i >= m_jugadores.length)
            i = -1;

        return i;
    }

    public Jugador findJugador(int camiseta) {
        Jugador jugador = null;
        int i = indexOfJugador(camiseta);
        if (i > 0)
            jugador = m_jugadores[i];

        return jugador;
    }

    public void addGol(Jugador jugador) {
        jugador.addGol();
        m_golesFavor++;
    }

    public void addPartidoGanado() {
        m_ganados++;
    }

    public void addPartidoPerdido() {
        m_perdidos++;
    }

    public void addPartidoEmpatado() {
        m_empatados++;
    }

    public void addGolesAFavor(int goles) {
        m_golesFavor += goles;
    }

    public void addGolesEnContra(int goles) {
        m_golesContra += goles;
    }
}
