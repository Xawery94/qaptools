package configuration;

public final class ConfigMap {

    public static final int ITERATIONS = Integer.parseInt(System.getProperty("iterations", "10"));
    public static final String STATS_PATH_PATTERN = System.getProperty("stats.path.pattern", "%s/stats_%s-%s-[%s].csv");
    public static final String STATS_DIRECTORY_PATH = System.getProperty("stats.dir.path", "res");

    //OPTIMA
    public static final double Nug12_OPT = 578;
    public static final double Nug20_OPT = 2570;
    public static final double Tai35_OPT = 2422002;
    public static final double Wil50_OPT = 48816;
    public static final double Tai60_OPT = 7205962;
    public static final double Sko72_OPT = 66256;
    public static final double Sko90_OPT = 115534;
    public static final double Wil100_OPT = 273038;


    public static double optimum(int size) {
        double opt = 0;
        switch (size) {
            case 12:
                opt = Nug12_OPT;
                break;
            case 20:
                opt = Nug20_OPT;
                break;
            case 35:
                opt = Tai35_OPT;
                break;
            case 50:
                opt = Wil50_OPT;
                break;
            case 60:
                opt = Tai60_OPT;
                break;
            case 72:
                opt = Sko72_OPT;
                break;
            case 90:
                opt = Sko90_OPT;
                break;
            case 100:
                opt = Wil100_OPT;
                break;
            default:
                break;
        }

        return opt;
    }

}
