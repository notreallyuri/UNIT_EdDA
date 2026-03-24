import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class BinaryTree {
  Node root;

  public void clear() {
    root = null;
  }

  private void collectPreOrder(Node node, List<Integer> values) {
    if (node == null)
      return;
    values.add(node.value);
    collectPreOrder(node.left, values);
    collectPreOrder(node.right, values);
  }

  public List<Integer> getAllValues() {
    List<Integer> values = new ArrayList<>();
    collectPreOrder(root, values);
    return values;
  }

  private Node insertRecursive(Node current, int value) {
    if (current == null) {
      return new Node(value);
    }

    if (value < current.value) {
      current.left = insertRecursive(current.left, value);
    } else if (value > current.value) {
      current.right = insertRecursive(current.right, value);
    }
    return current;
  }

  private boolean verify(int value) {
    return !containsNode(root, value);
  };

  private boolean containsNode(Node current, int value) {
    if (current == null) {
      return false;
    }

    if (value == current.value) {
      return true;
    }

    return value < current.value
        ? containsNode(current.left, value)
        : containsNode(current.right, value);
  }

  public void insert(int value) {
    if (verify(value)) {
      root = insertRecursive(root, value);
    } else {
      JOptionPane.showMessageDialog(null,
          "O valor " + value + " já existe na árvore.",
          "Aviso",
          JOptionPane.WARNING_MESSAGE);
    }
  }



  public ArrayList<Integer> preOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    Search.NLR(root, list);
    return list;
  }

public ArrayList<Integer> inOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    Search.LNR(root, list);
    return list;
  }

public ArrayList<Integer> postOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    Search.LRN(root, list);
    return list;
  }
}
