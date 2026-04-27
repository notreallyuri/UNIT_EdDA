package com.college.tree;

import javax.swing.JOptionPane;

public class AVLTree extends BinaryTree {

  private int height(Node node) {
    if (node == null) return 0;
    return ((AVLNode) node).height;
  }

  private void updateHeight(AVLNode node) {
    node.height = 1 + Math.max(height(node.left), height(node.right));
  }

  private int getBalance(Node node) {
    if (node == null) return 0;
    return height(node.left) - height(node.right);
  }

  private Node rotateRight(Node y) {
    Node x = y.left;
    Node t2 = x.right;

    x.right = y;
    y.left = t2;

    updateHeight((AVLNode) y);
    updateHeight((AVLNode) x);

    return x;
  }

  private Node rotateLeft(Node x) {
    Node y = x.right;
    Node t2 = y.left;

    y.left = x;
    x.right = t2;

    updateHeight((AVLNode) x);
    updateHeight((AVLNode) y);

    return y;
  }

  @Override
  protected Node insertRecursive(Node current, int value) {
    if (current == null) {
      return new AVLNode(value);
    }

    if (value < current.value) {
      current.left = insertRecursive(current.left, value);
    } else if (value > current.value) {
      current.right = insertRecursive(current.right, value);
    } else {
      return current; // duplicatas ignoradas
    }

    updateHeight((AVLNode) current);

    int balance = getBalance(current);

    if (balance > 1 && value < current.left.value) {
      return rotateRight(current);
    }

    if (balance < -1 && value > current.right.value) {
      return rotateLeft(current);
    }

    if (balance > 1 && value > current.left.value) {
      current.left = rotateLeft(current.left);
      return rotateRight(current);
    }

    if (balance < -1 && value < current.right.value) {
      current.right = rotateRight(current.right);
      return rotateLeft(current);
    }

    return current;
  }

  @Override
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


  @Override
  public String getTreeType() {
    return "Árvore AVL";
  }
}