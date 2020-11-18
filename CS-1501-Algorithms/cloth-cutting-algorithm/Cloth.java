import java.util.ArrayList;

public class Cloth {

    private int dimX, dimY, finalValue;
    private ArrayList<Pattern> pattern;
    protected ArrayList<FittedPattern> coordinates, garments;

    protected Cloth(int dimX, int dimY, ArrayList<Pattern> pattern) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.pattern = pattern;
    }

    protected void optimize() {

        //array used as memo for algorithm
        int[][] cutMemo = new int[dimX + 1][dimY + 1];
        Tuple[][] tupleMemo =  new Tuple[dimX + 1][dimY + 1];
        Memo memo = new Memo(cutMemo,tupleMemo);

        //array is filled with -1 because -1 is used as a flag in the memo
        fillMemo(0, dimX, 0, dimY, cutMemo);

        //Calculates optimal value of cloth
        ArrayList<FittedPattern> answer = computeOptimal(dimX, dimY, pattern, memo, new ArrayList<>(), 0);
        coordinates = answer;
        finalValue = memo.getFittedPatternMemo()[dimX][dimY].getOptimalValue();

    }

    private void fillMemo(int x, int dimX, int y, int dimY, int[][] cutMemo) {
        if (x < dimX + 1) {
            recurseY(x, y, dimY, cutMemo);
            fillMemo(x + 1, dimX, y, dimY, cutMemo);
        }
        return;
    }

    private void recurseY(int x, int y, int dimY, int[][] cutMemo) {
        if (y < dimY + 1) {
            cutMemo[x][y] = -1;
            recurseY(x, y + 1, dimY, cutMemo);
        }
        return;
    }

    private ArrayList<FittedPattern> computeOptimal(int dimX, int dimY, ArrayList<Pattern> pattern, Memo memo, ArrayList<FittedPattern> currentFittedPattern, int optimalValue) {

        //if the current value is not currently stored in the memo
        if (memo.getCutMemo()[dimX][dimY] == -1) {

            //Base case, recursed through the entire cloth
            if (dimX == 0 || dimY == 0)  return currentFittedPattern;

            //checks if pattern fits and selects largest one that does
            Pattern matchedPattern = patternMatch(0, dimX, dimY, pattern, 0, null);

            if(matchedPattern != null) {
                optimalValue += matchedPattern.getValue();
                currentFittedPattern.add(new FittedPattern(0, 0, matchedPattern));
            }

            //optimizing cuts on x axis
            Tuple verticalResult = verticalCut(1, dimX, dimY, pattern, memo, optimalValue, currentFittedPattern);
            optimalValue = verticalResult.getOptimalValue();
            currentFittedPattern = verticalResult.getList();

            //optimizing cuts on y axis
            Tuple horizontalResult = horizontalCut(1, dimX, dimY, pattern, memo, optimalValue, currentFittedPattern);
            optimalValue = horizontalResult.getOptimalValue();
            currentFittedPattern = horizontalResult.getList();

            Tuple tempTuple = new Tuple(optimalValue, currentFittedPattern);;

            memo.getCutMemo()[dimX][dimY] = tempTuple.getOptimalValue();
            memo.getFittedPatternMemo()[dimX][dimY] = tempTuple;
        }

        ArrayList<FittedPattern> clone = new ArrayList<>();

        for (FittedPattern p: memo.getFittedPatternMemo()[dimX][dimY].getList()) {
            FittedPattern temp = new FittedPattern(p.getDimX(), p.getDimY(), p.getPattern());
            clone.add(temp);
        }

        return clone;
    }

    //Optimizes the x axis of the cloth
    private Tuple verticalCut(int x, int dimX, int dimY, ArrayList<Pattern> pattern, Memo memo, int optimalValue, ArrayList<FittedPattern> currFittedPattern) {

        //Base case
        if (x == dimX) return new Tuple(optimalValue, currFittedPattern);

        ArrayList<FittedPattern> rightPatternList = computeOptimal(dimX - x, dimY, pattern, memo, new ArrayList<>(), 0);
        ArrayList<FittedPattern> leftPatternList = computeOptimal(x, dimY, pattern, memo, new ArrayList<>(), 0);

        //Fixes offset in x axis
        for(FittedPattern p : rightPatternList)
            p.setDimX(p.getDimX() + x);

        //Merges the two lists of patterns into one list
        ArrayList<FittedPattern> xAxisPatterns = new ArrayList<>();
        xAxisPatterns.addAll(rightPatternList);
        xAxisPatterns.addAll(leftPatternList);

        if ((memo.getCutMemo()[dimX - x][dimY] + memo.getCutMemo()[x][dimY]) > optimalValue) {
            optimalValue = memo.getCutMemo()[dimX - x][dimY] + memo.getCutMemo()[x][dimY];
            currFittedPattern = xAxisPatterns;
        }

        //Recursive case
        return verticalCut(x + 1, dimX, dimY, pattern, memo, optimalValue, currFittedPattern);
    }

    //Optimizes the y axis of the cloth
    private Tuple horizontalCut(int y, int dimX, int dimY, ArrayList<Pattern> pattern, Memo memo, int optimalValue, ArrayList<FittedPattern> currFittedPattern) {

        //Base case
        if (y == dimY) return new Tuple(optimalValue, currFittedPattern);

        ArrayList<FittedPattern> bottomPatternList = computeOptimal(dimX, dimY - y, pattern, memo, new ArrayList<>(), 0);
        ArrayList<FittedPattern> topPatternList = computeOptimal(dimX, y, pattern, memo, new ArrayList<>(), 0);

        //Fixes offset in y axis
        for (FittedPattern p : bottomPatternList)
            p.setDimY(p.getDimY() + y);

        //Merges the two lists of patterns into one list
        ArrayList<FittedPattern> yAxisPatterns = new ArrayList<>();
        yAxisPatterns.addAll(bottomPatternList);
        yAxisPatterns.addAll(topPatternList);

        if ((memo.getCutMemo()[dimX][dimY - y] + memo.getCutMemo()[dimX][y]) > optimalValue) {
            optimalValue = memo.getCutMemo()[dimX][dimY - y] + memo.getCutMemo()[dimX][y];
            currFittedPattern = yAxisPatterns;
        }

        //Recursive case
        return horizontalCut(y + 1, dimX, dimY, pattern, memo, optimalValue, currFittedPattern);
    }

    //Finds highest value pattern to fit in area
    private Pattern patternMatch(int i, int dimX, int dimY, ArrayList<Pattern> pattern, int optimalValue, Pattern current) {

        //Base case
        if (i == pattern.size()) return current;

        //If pattern fits AND the value of the pattern in > the current value
        if ((pattern.get(i).getdimX() <= dimX) && (pattern.get(i).getdimY() <= dimY) && (pattern.get(i).getValue() > optimalValue)) {
            current = pattern.get(i);
            optimalValue = pattern.get(i).getValue();
        }

        //Recursive case
        return patternMatch(i + 1, dimX, dimY, pattern, optimalValue, current);
    }

    //Converts ArrayList coordinate object to String output
    protected String garments() {
        StringBuilder sb = new StringBuilder();
        System.out.println("Cuts Made:");
        sb.append('[');
        coordinateToString(sb, 0);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    private void coordinateToString(StringBuilder sb, int i) {
        if (i == coordinates.size())
            return;

        sb.append('[');
        sb.append(coordinates.get(i).getPattern().getName());
        sb.append(',');
        sb.append(coordinates.get(i).getDimX());
        sb.append(',');
        sb.append(coordinates.get(i).getDimY());
        sb.append("], ");

        coordinateToString(sb, i + 1);
    }

    //Returns the optimized value of the cloth
    protected int value() {
        System.out.print("Cloth Value: ");
        return finalValue;
    }

    protected void calculatePatterns() {
        garments = new ArrayList<>();
        for(FittedPattern p : coordinates) {
            garments.add(new FittedPattern(p.getDimX() + p.getPattern().getdimX(), p.getDimY() +
                    p.getPattern().getdimY(), p.getPattern().getName()));
        }
    }

}