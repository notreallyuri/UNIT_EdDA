import java.util.ArrayList;
public class Search {
    public static void NLR(Node node, ArrayList<Integer> list){
        if(node == null){
            return;
        }

        list.add(node.value); 
        NLR(node.left, list); 
        NLR(node.right, list);
    }
    public static void LNR(Node node, ArrayList<Integer> list){
        if (node == null){
            return;
        }

        LNR(node.left, list);
        list.add(node.value);
        LNR(node.right, list);
    }
    public static void LRN(Node node, ArrayList<Integer> list){
        if (node == null){
            return;
        }

        LRN(node.left, list);
        LRN(node.right, list);
        list.add(node.value);
    }
}