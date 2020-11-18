import java.util.ArrayList;

public class Tuple {

    private int optimalValue;
    private ArrayList<FittedPattern> list;

    protected Tuple(int optimalValue, ArrayList<FittedPattern> list) {
        this.optimalValue = optimalValue;
        this.list = list;
    }

    protected int getOptimalValue() {
        return optimalValue;
    }

    protected ArrayList<FittedPattern> getList() {
        return list;
    }
}







