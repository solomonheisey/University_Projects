import java.util.ArrayList;
import javax.swing.*;
import java.util.Date;

public class ClothCuttingAlgorithm {

    private final static int SLEEP = 100;

    public static void main(String[] args) {

        //ArrayList of pattern sizes, values, and names
        ArrayList<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(2,2,1,"A"));
        patterns.add(new Pattern(2,6,4,"B"));
        patterns.add(new Pattern(4,2,3,"C"));
        patterns.add(new Pattern(5,3,5,"D"));

        System.out.println(" ");

        if(patterns.size() == 0) {
            System.out.println("Please enter at least one pattern in order to determine the optimal value of the cloth");
            System.exit(1);
        }

        //Dimensions of the cloth to be cut
        int dimX = 40;
        int dimY = 25;
        int pixels = 30;

        if(pixels <= 0)
            System.out.println("The pixel value of " + pixels + " will not display anything");

        if(dimX < 0 || dimY < 0) {
            System.out.println("Invalid x, y, and pixel combination. Please try again");
            System.exit(1);
        }

        Cloth cutter = new Cloth(dimX, dimY, patterns);

        //Optimizes given cloth for the patterns entered to maximize value
        cutter.optimize();

        //Calculates coordinates of patterns fit
        cutter.calculatePatterns();

        //Prints maximum of given cloth for entered patterns
        System.out.println(cutter.value());
        System.out.println(" ");

        //Prints cuts
        System.out.println(cutter.garments());

        //Outputs graphics of cloth
        GUI graphic = new GUI(dimX, dimY, pixels);
        JFrame frame = new JFrame("Dark Mode Everything");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphic);
        frame.pack();
        frame.setVisible(true);

        int i = 0;
        for (FittedPattern p : cutter.coordinates) {
            sleep(SLEEP);
            graphic.drawRectangle(p.getDimX(), p.getDimY(), p.getPattern().getdimX(), p.getPattern().getdimY(),
                    cutter.garments.get(i).getPatternName());
            i++;
        }
    }

    private static void sleep(long milliseconds) {
        Date d ;
        long start, now ;
        d = new Date() ;
        start = d.getTime() ;
        do { d = new Date() ; now = d.getTime() ; } while ( (now - start) < milliseconds ) ;
    }
}


