public class CNode {

    public NodeData nodeData;
    public CNode lchild;
    public CNode rchild;

    public CNode(NodeData nodeData){
        this(nodeData,null,null);
    }

    public CNode(NodeData nodeData, CNode lchild, CNode rchild) {
        this.nodeData = nodeData;
        this.lchild = lchild;
        this.rchild = rchild;
    }

    public CNode copyNode() {
        CNode tempRoot = new CNode(nodeData);
        if(lchild != null)
            tempRoot.lchild = lchild.copyNode();
        if(rchild != null)
            tempRoot.rchild = rchild.copyNode();
        return tempRoot;
    }


}
