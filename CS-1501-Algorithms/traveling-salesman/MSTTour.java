import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class MSTTour {

    private static int WIDTH=1000, HEIGHT=1000, SEED=12345, WAIT=1000;

    public static void main(String[] args) {

        int numberOfCities;
        boolean verbose;
        ArrayList<City> cities;
        MST mst;
        numberOfCities = Integer.parseInt(args[0]);
        verbose = Boolean.parseBoolean(args[1]);

        System.out.println( "Running test with " + numberOfCities + " cities..." );

        cities = pseudorandomCities(numberOfCities,WIDTH,HEIGHT);
        mst = new MST(cities);

        System.out.println( "MST weight: " + mst.weight() );

        if (verbose) {
            System.out.println( "Cities: " + cities );
            System.out.println( "Tour: " + mst.tour() );

            JFrame mapFrame = new JFrame("MST-Walk Tour");
            Map map = new Map(WIDTH,HEIGHT);
            mapFrame.getContentPane().add(map);
            mapFrame.pack();
            mapFrame.setVisible(true);
            mapFrame.repaint();
            sleep(WAIT);
            map.paintTour(mst.tour());
        }
    }

    private static ArrayList<City> pseudorandomCities(int n, double maxX, double maxY) {
        Random r = new Random(SEED);
        ArrayList<City> result = new ArrayList<>();
        for (int i=0 ; i<n ; i++)
            result.add( new City("city"+i,r.nextDouble()*maxX,r.nextDouble()*maxY) );
        return result;
    }

    private static void sleep(long milliseconds) {
        Thread thread = new Thread();
        try { thread.sleep(milliseconds); }
        catch (Exception e) {}
    }
}

