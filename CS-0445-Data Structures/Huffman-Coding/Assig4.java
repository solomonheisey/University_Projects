import TreePackage.*;
import java.util.*;
import java.io.*;


public class Assig4 {

    public static void main(String args[]) throws FileNotFoundException {

        File file = new File(args[0]); //opens file
        Scanner input = new Scanner(file); //sets creates Scanner object for file

        BinaryNode<Character> root = new BinaryNode(); // empty tree
        buildTree(input, root); //builds tree with root object as root of tree

        input.close(); //closes file
        Scanner in = new Scanner(System.in);
        System.out.println("The Huffman Tree has been restored");
        byte choice = 0;
        do{ //loops until user selects the quit option
            System.out.println("Please choose from the following:");
            System.out.println("1) Encode a text string");
            System.out.println("2) Decode a Huffman string");
            System.out.println("3) Quit");
            choice = in.nextByte();
            switch (choice) {
                case 1: encode(root, in);break; //branches to encode method
                case 2: decode(root, in);break; //branches to decode method
                default: {
                    System.out.println("Good-bye");
                    System.exit(1); //closes program
                }
            }
        } while (choice != 3);
    }

    private static int leafNodeCounter = 0; //keeps track of the number of leaf nodes

    private static String leafLetters = ""; //creates a string to store the letters from the leaves

    private static void buildTree(Scanner input, BinaryNode<Character> curr){

            String line = input.nextLine(); //reads next line from file
            if (line.charAt(0) == 'I') {
               curr.setData('\0');
               BinaryNode<Character> temp = new BinaryNode<>();
               curr.setLeftChild(temp);
               buildTree(input, temp);
               temp = new BinaryNode<>();
               curr.setRightChild(temp);
               buildTree(input, temp);
            } else if(line.charAt(0) == 'L'){
                curr.setData(line.charAt(2));
                leafLetters += line.charAt(2); //adds 2nd char to the existing leafLetters String
                leafNodeCounter++; //increases number of leaf nodes
                return;
            }
    }

    private static void encode(BinaryNode<Character> root, Scanner in){

        resultIndex = 0;
        System.out.println("Enter a String from the following characters:");
        System.out.println(leafLetters);
        String stringToEncode = in.next();
        System.out.println("Huffman String:");
        int [] digits = new int[root.getHeight()]; //creates a temp array to store the digits from each letter when encoded
        String[] table = new String[leafNodeCounter]; //creates table to store values based on number of leaf nodes
        createTable(root, table,digits, 0); //creates table

        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for(int i = 0; i < stringToEncode.length(); i++){
            for(int j =0; j < table.length; j++){
                if(stringToEncode.charAt(i) == leafLetters.charAt(j)){
                    stringBuilder.append(table[j]);
                    stringBuilder.append("\n");
                    counter++;
                }
            }
        }
        if (counter != stringToEncode.length()) { //if the number of matching chars != the length of the original string
            System.out.println("There was an error in your text string");
            System.out.println();
        }else
            System.out.println(stringBuilder);
    }

    private static int resultIndex = 0; //index for keeping track of spot in table array

    private static void createTable(BinaryNode root, String[] table, int[] digits, int digitCounter) {

        if(root.getLeftChild() != null) {
            digits[digitCounter] = 0;
            createTable(root.getLeftChild(), table, digits, digitCounter+ 1);
        }
        if(root.getRightChild() != null) {
            digits[digitCounter] = 1;
            createTable(root.getRightChild(), table, digits, digitCounter+ 1);
        }
        if (root.isLeaf()){
            StringBuilder codedLetter = new StringBuilder();
            for(int i = 0; i < digitCounter; i++) //loops through current digits
                codedLetter.append(digits[i]);
            table[resultIndex] = codedLetter.toString(); //adds full digit to the final table
            resultIndex++;
        }
    }

    private static void decode(BinaryNode<Character> root, Scanner in) {

        resultIndex = 0;
        int[] digits = new int[root.getHeight()];
        String[] table = new String[leafNodeCounter]; //creates an encoding table
        createTable(root, table, digits, 0);

        System.out.println("Here is the encoding table:");
        for (int i = 0; i < table.length; i++){ //prints current encoding table
            System.out.print(leafLetters.charAt(i) + ": " + table[i]);
            System.out.println();
        }
        System.out.println("Please enter a Huffman string (one line, no spaces)");
        String encodedString = in.next();
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Text string:");
        boolean error = false;
        for(int i = 0; i < encodedString.length();) {
            BinaryNode<Character> curr = root;
            while (curr != null) {
                if (curr.getLeftChild() == null && curr.getRightChild() == null) {
                    stringBuilder.append(curr.getData());
                    break;
                } else {
                    try {
                        if (encodedString.charAt(i) == '0')
                            curr = curr.getLeftChild();
                        else
                            curr = curr.getRightChild();
                        i++;
                    } catch(StringIndexOutOfBoundsException exception){
                        System.out.println("There was an error in your Huffman String");
                        error = true;
                        break;
                    }
                }
            }
        }
       if(!error){
           System.out.println(stringBuilder);
           System.out.println();
       } else
           System.out.println();
    }
}








