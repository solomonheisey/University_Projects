import java.util.Random;

public class OptimizeBST {

    // The following static variables set up an experiment to build
    // several optimized BST's with 10 to 100 keys, each with frequency
    // between 1 and 10000.

    public static int MIN_KEYS = 10;
    public static int MAX_KEYS = 100;
    public static int INCREMENT = 1;
    public static int MIN_FREQUENCY = 1;
    public static int MAX_FREQUENCY = 10000;

    public static void main(String[] args) {

        Random R = new Random();
        BinaryTree bestBST;
        BSTOptimizer optimizer;

        // The following code creates a new BST optimizer and populates it
        // with keys and frequencies for a small example.

        System.out.println("CREATE SMALL EXAMPLE");
        optimizer = new BSTOptimizer();
        optimizer.addKey("A",4);
        optimizer.addKey("F",2);
        optimizer.addKey("B",2);
        optimizer.addKey("C",1);
        optimizer.addKey("D",3);
        optimizer.addKey("G",1);
        optimizer.addKey("E",5);


        // The following code creates an optimal BST and prints it, its
        // cost, and the number of recursive calls required to build it with
        // memoization turned on.

        System.out.println("RUN SMALL EXAMPLE WITH MEMOIZATION");
        optimizer.MEMOIZE = true;
        optimizer.CALLS = 0;
        bestBST = optimizer.optimize();
        System.out.println( "  Optimal BST: " + bestBST);
        System.out.println( "  Cost: " + bestBST.cost);
        System.out.println( "  Recursive calls: " + optimizer.CALLS );

        // The following code creates an optimal BST and prints it, its
        // cost, and the number of recursive calls required to build it with
        // memoization turned off.

        System.out.println("RUN SMALL EXAMPLE WITHOUT MEMOIZATION");
        optimizer.MEMOIZE = false;
        optimizer.CALLS = 0;
        bestBST = optimizer.optimize();
        System.out.println( "  Optimal BST: " + bestBST);
        System.out.println( "  Cost: " + bestBST.cost);
        System.out.println( "  Recursive calls: " + optimizer.CALLS );

        // The following code runs a battery of experiments to create
        // optimal BST's with more and more keys, each with a random
        // frequency, with memoization turned on. It also checks that the
        // number of recursive calls needed to build it is proportional to
        // the cube of the number of keys.

        System.out.println("TEST RUNTIME WITH MEMOIZATION (KEYS CALLS CALLS/KEYS^3)");
        for (int keys=MIN_KEYS ; keys<=MAX_KEYS ; keys+=INCREMENT) {
            optimizer = new BSTOptimizer();
            optimizer.MEMOIZE = true;
            optimizer.CALLS = 0;
            for (int k=0 ; k<keys ; k++) optimizer.addKey("KEY-"+k,R.nextInt(MAX_FREQUENCY));
            bestBST = optimizer.optimize();
            System.out.printf("  %4d  %10d  %15f \n", keys, optimizer.CALLS, (float)optimizer.CALLS/(keys*keys*keys));
        }

        // The following code runs a battery of experiments to create
        // optimal BST's with more and more keys, each with a random
        // frequency, with memoization turned off. It also demonstrates that
        // number of recursive calls needed to build it increases faster
        // than the cube of the number of keys.

        System.out.println("TEST RUNTIME WITHOUT MEMOIZATION (KEYS CALLS CALLS/KEYS^3)");
        for (int keys=MIN_KEYS ; keys<=MAX_KEYS ; keys+=INCREMENT) {
            optimizer = new BSTOptimizer();
            optimizer.MEMOIZE = false;
            optimizer.CALLS = 0;
            for (int k=0 ; k<keys ; k++) optimizer.addKey("KEY-"+k,R.nextInt(MAX_FREQUENCY));
            bestBST = optimizer.optimize();
            System.out.printf("  %4d  %10d  %15f \n", keys, optimizer.CALLS, (float)optimizer.CALLS/(keys*keys*keys));
        }
    }
}