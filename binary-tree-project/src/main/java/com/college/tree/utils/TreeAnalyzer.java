package com.college.tree.utils;

import com.college.tree.Node;

public class TreeAnalyzer {
  public static int getHeight(Node node) {
    if (node == null)
      return -1;
    return 1 + Math.max(getHeight(node.left), getHeight(node.right));
  }

  public static String getTreeType(Node root) {
    if (root == null)
      return "Árvore vazia";

    int depth = getDepth(root);

    boolean full = isFull(root);
    boolean complete = isComplete(root, 0, countNodes(root));
    boolean perfect = full && isPerfect(root, depth, 0);
    boolean degenerate = isDegenerate(root);

    if (degenerate)
      return "Árvore Degenerada (lista encadeada)";
    if (perfect)
      return "Árvore Perfeita";
    if (full)
      return "Árvore Cheia";
    if (complete)
      return "Árvore Completa";

    return "Árvore Não Completa";
  }

  public static int countNodes(Node node) {
    if (node == null)
      return 0;
    return 1 + countNodes(node.left) + countNodes(node.right);
  }

  private static boolean isFull(Node node) {
    if (node == null)
      return true;
    if ((node.left == null && node.right == null))
      return true;
    if (node.left != null && node.right != null)
      return isFull(node.left) && isFull(node.right);
    return false;
  }

  private static int getDepth(Node node) {
    int depth = 0;
    while (node != null) {
      depth++;
      node = node.left;
    }
    return depth;
  }

  private static boolean isPerfect(Node node, int depth, int level) {
    if (node == null)
      return true;

    if (node.left == null && node.right == null)
      return depth == level;

    if (node.left == null || node.right == null)
      return false;

    return isPerfect(node.left, depth, level + 1) &&
        isPerfect(node.right, depth, level + 1);
  }

  private static boolean isComplete(Node node, int index, int total) {
    if (node == null)
      return true;
    if (index >= total)
      return false;

    return isComplete(node.left, 2 * index + 1, total) &&
        isComplete(node.right, 2 * index + 2, total);
  }

  private static boolean isDegenerate(Node node) {
    if (node == null)
      return true;

    if (node.left != null && node.right != null)
      return false;

    if (node.left != null)
      return isDegenerate(node.left);

    return isDegenerate(node.right);
  }
}
