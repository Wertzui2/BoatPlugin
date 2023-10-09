import java.util.Comparator;

public class NodeComparator implements Comparator<int[]> {
    public int compare(int[] node1, int[] node2){ //returns lower f
        if(node1[0]>node2[0])
            return -1;
        if(node1[0]<node2[0])
            return 1;
        return 0;
    }

}
