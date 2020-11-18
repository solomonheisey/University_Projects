public class BinaryTree {

    public int cost;
    public CNode root;

    public BinaryTree() {
        cost = Integer.MAX_VALUE;
        root = null;
    }

    public BinaryTree(NodeData nodeData, BinaryTree ltree, BinaryTree rtree) {
        root = new CNode(nodeData);

        if((ltree != null) && (ltree.root != null))
            root.lchild = ltree.root.copyNode();

        if((rtree != null) && (rtree.root != null))
            root.rchild = rtree.root.copyNode();

        this.cost = (ltree == null ? 0 :  ltree.cost) + (rtree == null ? 0 : rtree.cost);
    }

    //Prints the String version of the BST (Preorder)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        toStringRecursive(sb, this.root);
        sb.append(')');
        return sb.toString();
    }

    //Recurses through the optimal BST and prints result
    private void toStringRecursive(StringBuilder sb, CNode root) {

        if (root == null) {
            sb.append(" null");
            return;
        }

        sb.append(root.nodeData.key);

        if (root.lchild == null )
            sb.append(" null ");
        else {
            sb.append(" (");
            toStringRecursive(sb, root.lchild);
            sb.append(')');
        }

       if (root.rchild == null)
           sb.append(" null");
       else {
            sb.append('(');
            toStringRecursive(sb, root.rchild);
            sb.append(')');
        }

    }
}
