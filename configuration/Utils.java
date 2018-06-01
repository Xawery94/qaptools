package configuration;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public int[] randomPermutation(int size) {
        int[] a = new int[size];
        // insert integers 0..n-1
        for (int i = 0; i < size; i++) {
            a[i] = i;
        }
        // shuffle
        for (int i = 0; i < size; i++) {
            int r = (int) (Math.random() * (i + 1));     // int between 0 and i
            int swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }

        return a;
    }

    public double calculateCost(int[] solution, Data data) {
        int cost = 0;

        for (int i = 0; i < data.getSize(); i++) {  //wiersz
            for (int j = 0; j < data.getSize(); j++) {  //kolumna
                cost = cost + data.getMatrixA(i, j) * data.getMatrixB(solution[i], solution[j]);
            }
        }

        return cost;
    }

    public int calculateOptimumDistance(double wyliczoneOptimum, double opt) {
        double roznica = wyliczoneOptimum - opt;
        double procenty = roznica / opt;

        return (int) Math.round(procenty * 100);
    }

    public List<int[]> getNeighbours(int[] currentSolution) {
        List<int[]> listNeighbours = new ArrayList<>();

        for (int i = 0; i < currentSolution.length; i++) {
            int a = currentSolution[i];
            for (int j = 0; j < currentSolution.length; j++) {
                int b = currentSolution[j];
                int[] neighbour = currentSolution.clone();

                neighbour[i] = b;
                neighbour[j] = a;

                if (j > i) {
                    listNeighbours.add(neighbour);
                }
            }
        }

        return listNeighbours;
    }
}
