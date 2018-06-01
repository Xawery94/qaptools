package algorithms;

import configuration.Data;
import configuration.StatsFile;
import configuration.Utils;

import java.time.Instant;
import java.util.List;

import static configuration.ConfigMap.ITERATIONS;

public class Steepest {
    private final Utils utils;
    private final Data data;
    private final StatsFile stats;
    private final double opt;

    public Steepest(Utils utils, Data data, double opt, StatsFile stats) {
        this.utils = utils;
        this.data = data;
        this.opt = opt;
        this.stats = stats;
    }

    public void run() {
        boolean running;
        List<int[]> list;
        double bestCost;
        double neighbourCost;
        double bestNeighbourCost;

        Instant start = Instant.now();
        for (int j = 0; j < ITERATIONS; j++) {
            int steps = 0;
            int numberSolutions = 0;
            int[] currentSolutions = utils.randomPermutation(data.getSize());
            int[] bestNeighbour = new int[0];
            bestCost = utils.calculateCost(currentSolutions, data);

            running = true;
            while (running) {
                steps++;
                list = utils.getNeighbours(currentSolutions);
                running = false;
                bestNeighbourCost = bestCost;
                for (int i = 0; i < list.size(); i++) {
                    numberSolutions++;
                    int[] neighbour = list.get(i);
                    neighbourCost = utils.calculateCost(neighbour, data);
                    if (neighbourCost < bestNeighbourCost) {
                        bestNeighbourCost = neighbourCost;
                        bestNeighbour = neighbour;
                    }
                }

                if (bestNeighbourCost < bestCost) {
                    currentSolutions = bestNeighbour;
                    bestCost = bestNeighbourCost;
                    running = true;
                }
            }

            Instant end = Instant.now();
            long execTime = end.toEpochMilli() - start.toEpochMilli();

            System.out.println(execTime);

            stats.solution(bestCost, steps, numberSolutions, utils.calculateOptimumDistance(bestCost, opt), execTime);
        }
    }
}