import Torneo.Torneo;
import Torneo.UI;

public class Main {
    public static void main(String[] args) {
        // creo el torneo con los archivos de equipos y jugadores
        Torneo torneo = new Torneo("Equipos.txt", "Jugadores.txt");

        // corro el programa
        UI ui = new UI(torneo);
        ui.run();
    }
}
