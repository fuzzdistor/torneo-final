import Torneo.Torneo;

public class Main {

    public static void main(String[] args) {
        Torneo torneo = new Torneo("Equipos.txt", "Jugadores.txt");
        System.out.println("Equipos:");
        for (int i = 0; i < torneo.getEquipos().length; i++) {
            System.out.println(torneo.getEquipos()[i].getNombre());
        }
    }
}
