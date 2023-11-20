
import java.io.Serializable;
import java.util.Comparator;
public class HuffmanNode implements Serializable {
    byte data; // Use byte instead of char to handle binary files;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;
}

class FrequencyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.frequency - y.frequency;
    }
}
