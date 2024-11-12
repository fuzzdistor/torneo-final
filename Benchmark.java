import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import Torneo.Jugador;
import Utils.Utils;

public class Benchmark {
    public class Data {
        private long[] times;
        private int index;

        Data(int iterations) {
            times = new long[iterations];
            index = 0;
        }

        public void addTime(long time) {
            times[index] = time;
            index++;
        }

        public double getMedian() {
            long count = 0;
            for (long time : times) {
                count += time;
            }

            return (double) count / times.length;
        }

        public long getMin() {
            long min = times[0];
            for (long time : times) {
                min = Math.min(min, time);
            }

            return min;
        }

        public long getMax() {
            long max = times[0];
            for (long time : times) {
                max = Math.max(max, time);
            }

            return max;
        }

        public void printTimes() {
            for (long time : times) {
                System.out.print(time);
                System.out.print(',');
            }
            System.out.println();
        }

        public void printMedian() {
            System.out.print(getMedian());
            System.out.println(',');
        }
    }

    public Data[] benchmark(int elementos, int iteraciones) {
        Data[] d = new Data[2];
        d[0] = new Data(iteraciones);
        d[1] = new Data(iteraciones);
        Data dataQuick = d[0];
        Data dataSelection = d[1];

        Jugador[] jugadores = Utils.generarJugadoresRandom(elementos);
        Utils.mix(jugadores);


        for (int i = 0; i < iteraciones; i++) {
            long start = System.nanoTime();
            Utils.sortJugadoresPorApellidoNombreQuick(jugadores);
            long t = System.nanoTime() - start;
            dataQuick.addTime(t);
            Utils.mix(jugadores);
        }

        for (int i = 0; i < iteraciones; i++) {
            long start = System.nanoTime();
            Utils.sortJugadoresPorApellidoNombreSelection(jugadores);
            long t = System.nanoTime() - start;
            dataSelection.addTime(t);
            Utils.mix(jugadores);
        }

        return d;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            args = new String[2];
            args[0] = "1000";
            args[1] = "10000";
        }
        Benchmark b = new Benchmark();
        Data[] d = b.benchmark(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(args[0] + "," + args[1] + ",");
        d[0].printTimes();
        d[0].printMedian();
        d[1].printTimes();
        d[1].printMedian();
    }
}
