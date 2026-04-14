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

  public static void NLR(Node node, ArrayList<Integer> list) {
    if (node == null) {
      return;
    }

    list.add(node.value);
    NLR(node.left, list);
    NLR(node.right, list);
  }

  public static void LNR(Node node, ArrayList<Integer> list) {
    if (node == null) {
      return;
    }

    LNR(node.left, list);
    list.add(node.value);
    LNR(node.right, list);
  }

  public static void LRN(Node node, ArrayList<Integer> list) {
    if (node == null) {
      return;
    }

    LRN(node.left, list);
    LRN(node.right, list);
    list.add(node.value);
  }

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
