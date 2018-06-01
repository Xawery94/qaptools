package algorithms;

import configuration.Data;
import configuration.StatsFile;
import configuration.Utils;

import java.time.Instant;

import static configuration.ConfigMap.ITERATIONS;

public class RandomSearch {
    private final Utils utils;
    private final Data data;
    private final StatsFile stats;
    private final double opt;

    public RandomSearch(Utils utils, Data data, double opt, StatsFile stats) {
        this.utils = utils;
        this.data = data;
        this.opt = opt;
        this.stats = stats;
    }

    public void run() {
        double bestCost;
        double cost;
        boolean running;
        int worse;
        int steps;
        int innerIterations;

        Instant start = Instant.now();
        for (int j = 0; j < ITERATIONS; j++) {
            innerIterations = 25000/8*data.getSize();
            worse = 0;
            steps = 0;
            running = true;
            bestCost = utils.calculateCost(utils.randomPermutation(data.getSize()), data);

            while (running) {
                steps++;
                cost = utils.calculateCost(utils.randomPermutation(data.getSize()), data);

                if (cost < bestCost) {
                    worse = 0;
                    bestCost = cost;
                } else {
                    worse++;
                }

                if (worse >= innerIterations) {
                    running = false;
                }
            }

            Instant end = Instant.now();
            long execTime = end.toEpochMilli() - start.toEpochMilli();

            System.out.println(execTime);

            stats.solution(bestCost, steps, steps, utils.calculateOptimumDistance(bestCost, opt), execTime);
        }
    }

}
