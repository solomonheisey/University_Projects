public class FittedPattern {

    private Pattern pattern;
    private int dimX, dimY;
    private String patternName;

    protected FittedPattern(int dimX, int dimY, Pattern pattern) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.pattern = pattern;
    }

    protected FittedPattern(int dimX, int dimY, String patternName) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.patternName = patternName;
    }

    protected int getDimX() {
        return dimX;
    }

    protected int getDimY() {
        return dimY;
    }

    protected String getPatternName() { return patternName; }

    protected Pattern getPattern() {
        return pattern;
    }

    protected void setDimX(int dimX) {
        this.dimX = dimX;
    }

    protected void setDimY(int dimY) {
        this.dimY = dimY;
    }
}
