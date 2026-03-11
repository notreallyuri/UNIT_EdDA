import java.util.ArrayList;
public class Search {
    public void NLR(Node node, ArrayList<Integer> list){
        if(node == null){
            return;
        }

        list.add(node.value);   // N
        NLR(node.left, list);   // L
        NLR(node.right, list);  // R
    }
    public void LNR(Node node, ArrayList<Integer> list){
        if (node == null){
            return;
        }

        LNR(node.left, list);
        list.add(node.value);
        LNR(node.right, list);
    }
    public void LRN(Node node, ArrayList<Integer> list){
        if (node == null){
            return;
        }

        LRN(node.left, list);
        LRN(node.right, list);
        list.add(node.value);
    }
}