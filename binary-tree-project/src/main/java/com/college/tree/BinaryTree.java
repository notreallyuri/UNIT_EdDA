package com.college.tree;

import java.util.*;
import javax.swing.JOptionPane;
import com.college.tree.utils.*;

public class BinaryTree {
  public Node root;

  public void clear() {
    root = null;
  }

  public List<Integer> getAllValues() {
    return preOrder();
  }

  public String getTreeType() {
    return TreeAnalyzer.getTreeType(root);
  }

  public List<Integer> getPathsTo(int value) {
    return TreeSearch.getPathTo(this.root, value);
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
    if (!containsNode(root, value)) {
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
    TreeSearch.NLR(root, list);
    return list;
  }

  public ArrayList<Integer> inOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    TreeSearch.LNR(root, list);
    return list;
  }

  public ArrayList<Integer> postOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    TreeSearch.LRN(root, list);
    return list;
  }
}
