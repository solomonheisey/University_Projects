public class Pattern {

    private int dimX;
    private int dimY;
    private int value;
    private String name;

    protected Pattern(int dimX, int dimY, int value, String name) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.value = value;
        this.name = name;
    }

    protected int getdimX() {
        return dimX;
    }

    protected int getdimY() {
        return dimY;
    }

    protected int getValue() {
        return value;
    }

    protected String getName() {
        return name;
    }

}
