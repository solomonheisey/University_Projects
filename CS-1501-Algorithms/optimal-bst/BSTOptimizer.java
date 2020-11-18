import java.util.*;

public class BSTOptimizer {

    public boolean MEMOIZE;
    public int CALLS;
    public List<NodeData> nodeDataList;

    public BSTOptimizer() {
        nodeDataList = new ArrayList<>();
    }

    public void addKey(String key, int frequency) {
        nodeDataList.add(new NodeData(key, frequency));
    }

    public BinaryTree optimize() {
        int nodeNumber = nodeDataList.size();

        BinaryTree[][] binaryTreeMemo = new BinaryTree[nodeNumber + 1][nodeNumber + 1];

        //Sorts nodeDataList based on value of key
        Collections.sort(nodeDataList, NodeData.COMPARE_BY_KEY);

        //Returns optimal BST
        return optimizeTree(0, nodeNumber - 1, binaryTreeMemo);
    }

    //Returns optimal BST
    private BinaryTree optimizeTree(int i, int j, BinaryTree[][] binaryTreeMemo) {
        this.CALLS++;

        if (i > j)
            return null;

        if(binaryTreeMemo[i + 1][j + 1] != null && this.MEMOIZE)
            return binaryTreeMemo[i + 1][j + 1];

        int k = i;
        BinaryTree optimalTree= new BinaryTree();
        optimalTree = calculateOptimalTree(k, i, j, optimalTree, binaryTreeMemo);

        binaryTreeMemo[i + 1][j + 1] = optimalTree;

        return optimalTree;
    }

    //Recursive function to optimize list of keys and frequencies, returns optimal BST
    private BinaryTree calculateOptimalTree(int k, int i, int j, BinaryTree optimalTree, BinaryTree[][] binaryTreeMemo) {

        if (k <= j) {

            //sets root based off of current position
            NodeData rootData = nodeDataList.get(k);

            //Recursively optimizes left side of tree
            BinaryTree ltree = optimizeTree(i, k - 1, binaryTreeMemo);

            //Recursively optimizes right side of tree
            BinaryTree rtree = optimizeTree(k + 1, j, binaryTreeMemo);

            //Assembles tree using current values
            BinaryTree tree = new BinaryTree(rootData, ltree, rtree);

            List<NodeData> tempNodeDataList = nodeDataList.subList(i, j + 1);

            //Sum of frequencies
            tree = frequencySum(tempNodeDataList,0,tree);

            //Compares cost of previous optimal cost to current optimal cost
            if (tree.cost < optimalTree.cost)
                optimalTree = tree;

            return calculateOptimalTree(k + 1, i, j, optimalTree, binaryTreeMemo);
        }
        return optimalTree;
    }

    //Summation of all frequencies from i to j
    private BinaryTree frequencySum(List<NodeData> tempNodeDataList, int i, BinaryTree tree) {

        if (i < tempNodeDataList.size()) {

            tree.cost += tempNodeDataList.get(i).frequency;
            return frequencySum(tempNodeDataList, i+1, tree);

        }
        return tree;
    }



}
