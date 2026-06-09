package com.college.tree.utils;

import com.college.tree.Node;
import java.util.ArrayList;
import java.util.List;

public class TreeSearch {

  public static List<Integer> getPathTo(Node root, int value) {
    List<Integer> path = new ArrayList<>();
    if (findPath(root, value, path)) {
      return path;
    }
    return new ArrayList<>();
  }

  private static boolean findPath(Node node, int value, List<Integer> path) {
    if (node == null)
      return false;

    path.add(node.value);

    if (node.value == value)
      return true;

    if (value < node.value) {
      if (findPath(node.left, value, path))
        return true;
    } else {
      if (findPath(node.right, value, path))
        return true;
    }

    path.remove(path.size() - 1);
    return false;
  }

  public static List<Integer> getPreOrder(Node node) {
    List<Integer> result = new ArrayList<>();
    accumulatePreOrder(node, result);
    return result;
  }

  private static void accumulatePreOrder(Node node, List<Integer> result) {
    if (node == null)
      return;
    result.add(node.value);
    accumulatePreOrder(node.left, result);
    accumulatePreOrder(node.right, result);
  }

  public static List<Integer> getInOrder(Node node) {
    List<Integer> result = new ArrayList<>();
    accumulateInOrder(node, result);
    return result;
  }

  private static void accumulateInOrder(Node node, List<Integer> result) {
    if (node == null)
      return;
    accumulateInOrder(node.left, result);
    result.add(node.value);
    accumulateInOrder(node.right, result);
  }

  public static List<Integer> getPostOrder(Node node) {
    List<Integer> result = new ArrayList<>();
    accumulatePostOrder(node, result);
    return result;
  }

  private static void accumulatePostOrder(Node node, List<Integer> result) {
    if (node == null)
      return;
    accumulatePostOrder(node.left, result);
    accumulatePostOrder(node.right, result);
    result.add(node.value);
  }

  // --- Tree Metrics ---

  public static int height(Node node) {
    if (node == null) {
      return -1;
    }
    return 1 + Math.max(height(node.left), height(node.right));
  }

  public static int depth(Node raiz, Node alvo) {
    if (raiz == null)
      return -1;
    if (raiz == alvo)
      return 0;

    int profundidadeEsq = depth(raiz.left, alvo);
    if (profundidadeEsq >= 0)
      return profundidadeEsq + 1;

    int profundidadeDir = depth(raiz.right, alvo);
    if (profundidadeDir >= 0)
      return profundidadeDir + 1;

    return -1;
  }
}
