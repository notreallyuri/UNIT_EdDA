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
    public static int height(Node node){
        if (node == null){
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    int profundidade(Node raiz, Node alvo) {
        if (raiz == null) return -1;
        if (raiz == alvo) return 0;
        
        int profundidadeEsq = profundidade(raiz.left, alvo);
        if (profundidadeEsq >= 0) return profundidadeEsq + 1;
        
        int profundidadeDir = profundidade(raiz.right, alvo);
        if (profundidadeDir >= 0) return profundidadeDir + 1;
        
        return -1;
    }
}