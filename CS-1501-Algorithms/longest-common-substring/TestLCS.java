public class TestLCS {

    public static void main(String[] args) {
        String word1, word2;
        word1 = args[0];
        word2 = args[1];
        System.out.print("LCS of " + word1 + " and " + word2 + ": ");
        System.out.println(LCS.findLCS(word1,word2));
    }
}
