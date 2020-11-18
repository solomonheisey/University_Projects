import java.util.Comparator;

public class NodeData {
    public int frequency;
    public String key;

    public NodeData(String key, int frequency) {
        this.key = key;
        this.frequency = frequency;
    }

    public static Comparator<NodeData> COMPARE_BY_KEY = Comparator.comparing(o -> o.key);
}
