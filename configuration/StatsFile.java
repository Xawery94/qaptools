package configuration;

import java.io.IOException;
import java.io.Writer;

public interface StatsFile extends AutoCloseable {

    void solution(double bestCost, int steps, int ratedSolutions, int opt, double execTime);

    void close() throws IOException;

    static StatsFile noop() {
        return new StatsFile() {
            @Override
            public void close() {

            }

            @Override
            public void solution(double bestCost, int steps, int ratedSolutions, int opt, double execTime) {
            }
        };
    }

    static StatsFile file(Writer writer) {
        try {
            writer.write("bestCost;steps;ratedSolutions;opt;execTime\n");
        } catch (IOException exc) {
        }

        return new StatsFile() {
            @Override
            public void close() throws IOException {
                writer.close();
            }

            @Override
            public void solution(double bestCost, int steps, int ratedSolutions, int opt, double execTime) {
                write(bestCost, steps, ratedSolutions, opt, execTime);
            }

            private void write(double bestCost, int steps, int ratedSolutions, int opt, double execTime) {
                try {
                    writer.write(String.format("%d;%d;%d;%d;%d\n",
                            (int) bestCost,
                            steps,
                            ratedSolutions,
                            opt,
                            (int) execTime));
                    writer.flush();
                } catch (IOException exc) {
                }
            }
        };
    }
}
