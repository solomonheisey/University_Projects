class LCS {

    //Method returns String of largest substring of two strings
    static String findLCS(String word1, String word2) {

        //Array is used as memo for algorithm
        //Then array is filled with flag value of -1
        int[][] memo = new int[word1.length() + 1][word2.length() + 1];
        fillMemo(0, word1.length(), 0, word2.length(), memo);

        //Initializes StringBuilder object and calls recursive method
        StringBuilder substring = new StringBuilder();
        findLCSRecursive(word1, word2, 0,0 , memo, substring);

        //Reverses StringBuilder and casts it to String
        return substring.reverse().toString();
    }

    //Method returns StringBuilder with largest substring of two strings
    private static void findLCSRecursive(String word1, String word2, int i, int j, int[][] memo, StringBuilder answer) {
        //If no value is being stored in memo
        if(memo[i][j] == -1) {
            //Base case where iterators reach end of String
            if (i == word1.length() || j == word2.length())
                memo[i][j] = 0;
            //Move increment iterator, store value in memo and append value
            else if (word1.charAt(i) == word2.charAt(j)) {
                findLCSRecursive(word1, word2, i + 1, j + 1, memo, answer);
                memo[i][j] = 1 + answer.length();
                answer.append(word1.charAt(i));
            }else {
                findLCSRecursive(word1,word2, i+1, j, memo, answer);
                int max1 = answer.length();
                findLCSRecursive(word1,word2, i, j+1, memo, answer);
                int max2  = answer.length();
                //Otherwise choose the find the largest substring and update memo
                memo[i][j] = Math.max(max1, max2);
            }
        }
    }

    private static void fillMemo(int i, int word1, int j, int word2, int[][] memo) {
        if (i < word1 + 1) {
            recurseY(i, j, word2, memo);
            fillMemo(i + 1, word1, j, word2, memo);
        }
        return;
    }

    private static void recurseY(int i, int j, int word2, int[][] memo) {
        if (j < word2 + 1) {
            memo[i][j] = -1;
            recurseY(i, j + 1, word2, memo);
        }
        return;
    }

}
