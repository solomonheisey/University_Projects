public class vmsim {

    //PAGE_SIZE contains page size in bytes
    private static int PAGE_SIZE;
    private static String PATH;
    private static int FRAMES;
    private static String MEMORY_SPLIT;

    public static void main(String[] args) {
        parseArguments(args);
        int[] split = parseSplit();
	    SCA sca = new SCA(PATH, PAGE_SIZE, split);
	    sca.runSimulation();
	    System.out.println(sca);
    }

    //parses command line arguments
    private static void parseArguments(String[] args) {
        FRAMES = Integer.parseInt(args[1]);
        PAGE_SIZE = Integer.parseInt(args[3])*1024;
        MEMORY_SPLIT = args[5];
        PATH = args[6];

        if(PATH.contains("64"))
            System.exit(1);
    }

    //generates memory split given number of frames
    private static int[] parseSplit() {
        String[] split = MEMORY_SPLIT.split(":");
        int a = Integer.parseInt(split[0]);
        int b = Integer.parseInt(split[1]);

        int newA = a;
        int newB = b;

        int i = 1;
        while(newA + newB  < FRAMES) {
            newA = a * i;
            newB = b * i;
            i++;
        }

        if(newA+newB != FRAMES)
            System.exit(1);

        int[] values = new int[2];
        values[0] = newA;
        values[1] = newB;

        return values;
    }
}
