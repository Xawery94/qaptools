import algorithms.Greedy;
import algorithms.RandomSearch;
import algorithms.Steepest;
import configuration.ConfigMap;
import configuration.Data;
import configuration.StatsFile;
import configuration.Utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static configuration.ConfigMap.STATS_DIRECTORY_PATH;
import static configuration.ConfigMap.optimum;

public class Main {

    private static StatsFile stats(int size, String method) throws IOException {
        new File(STATS_DIRECTORY_PATH).mkdirs();

        final String pathPattern = ConfigMap.STATS_PATH_PATTERN;
        if (pathPattern == null) {
            return StatsFile.noop();
        }

        final String timestamp = DateTimeFormatter
                .ofPattern("kk-mm-ss")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
        final Path path = Paths.get(String.format(ConfigMap.STATS_PATH_PATTERN, STATS_DIRECTORY_PATH, method, size, timestamp));
        final OutputStream stream = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.SYNC);

        return StatsFile.file(new OutputStreamWriter(stream));
    }

    public static void main(String[] args) {
        Utils utils = new Utils();
        File folder = new File("temp/");
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Data data = new Data("temp/" + file.getName());
                double opt = optimum(data.getSize());

                try (final StatsFile stats = stats(data.getSize(), "steepest")) {
                    Instant start = Instant.now();
                    System.err.println("Steepest: " + data.getSize() + " " + start);
                    Steepest steepest = new Steepest(utils, data, opt, stats);
                    steepest.run();

                } catch (IOException exc) {
                    System.err.print("Stats error: " + exc);
                }

                try (final StatsFile stats = stats(data.getSize(), "greedy")) {
                    Instant start = Instant.now();
                    System.err.println("Greedy: " + data.getSize() + " " + start);
                    Greedy greedy = new Greedy(utils, data, opt, stats);
                    greedy.run();

                } catch (IOException exc) {
                    System.err.print("Stats error: " + exc);
                }

                try (final StatsFile stats = stats(data.getSize(), "random")) {
                    Instant start = Instant.now();
                    System.err.println("Random: " + data.getSize() + " " + start);
                    RandomSearch randomSearch = new RandomSearch(utils, data, opt, stats);
                    randomSearch.run();

                } catch (IOException exc) {
                    System.err.print("Stats error: " + exc);
                }

            }
        }

        System.err.println("Finished work");

    }
}
