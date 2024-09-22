import Torneo.Torneo;
import Torneo.UI;
import Utils.Utils;

public class Main {

    public static void main(String[] args) {
        Torneo torneo = new Torneo("Equipos.txt", "Jugadores.txt");
        for (int i = 0; i < torneo.getEquipos().length; i++) {
            torneo.getEquipos()[i].addPartido(i, 4);
        }

        UI.run(torneo);


        UI.printLeaderBoard(torneo);

        torneo.createFechas();

        UI.printFechas(torneo);
        UI.printFecha(torneo, 1);
    }
}
