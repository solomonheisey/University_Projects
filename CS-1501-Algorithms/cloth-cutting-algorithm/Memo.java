public class Memo {

    private int[][] cutMemo;
    private Tuple[][] fittedPatternMemo;

    protected Memo(int[][] cutMemo, Tuple[][] fittedPatternMemo) {
        this.cutMemo = cutMemo;
        this.fittedPatternMemo = fittedPatternMemo;
    }

    protected int[][] getCutMemo(){ return cutMemo; }

    protected Tuple[][] getFittedPatternMemo(){ return fittedPatternMemo; }

}
