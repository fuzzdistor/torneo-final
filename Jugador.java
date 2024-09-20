
public class Jugador {

    final private String m_apellido;
    final private String m_nombre;
    final private int m_edad;
    final private int m_dni;
    final private int m_camiseta;
    private Equipo m_equipo;
    private int m_goles;

    public Jugador(String apellido, String nombre, int edad, int dni, int camiseta, Equipo equipo) {
        m_nombre = apellido;
        m_apellido = nombre;
        m_edad = edad;
        m_dni = dni;
        m_camiseta = camiseta;
        m_equipo = equipo;
        m_goles = 0;
    }

    public Jugador(String apellido, String nombre, int edad, int dni, int camiseta) {
        m_apellido = apellido;
        m_nombre = nombre;
        m_edad = edad;
        m_dni = dni;
        m_camiseta = camiseta;
        m_equipo = null;
        m_goles = 0;
    }

    public void setEquipo(Equipo equipo) {
        m_equipo = equipo;
    }

    public String getNombre() {
        return m_nombre;
    }

    public String getApellido() {
        return m_apellido;
    }

    public int getEdad() {
        return m_edad;
    }

    public int getDni() {
        return m_dni;
    }

    public int getCamiseta() {
        return m_camiseta;
    }

    public int getGoles() {
        return m_goles;
    }

    public Equipo getEquipo() {
        return m_equipo;
    }

    public String toString() {
        return m_nombre + " "
                + m_apellido + " "
                + m_edad + " "
                + m_dni + " "
                + m_camiseta + " "
                + m_equipo;
    }

    public void setGoles(int goles) {
        m_goles = goles;
    }

    public void addGol() {
        m_goles += 1;
    }

    public int compareTo(Jugador otro) {
        int comp = m_nombre.compareToIgnoreCase(otro.m_nombre);
        if (comp == 0) {
            comp = m_apellido.compareToIgnoreCase(otro.m_apellido);
        }
        return comp;
    }
}
