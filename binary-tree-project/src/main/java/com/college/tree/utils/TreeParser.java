package com.college.tree.utils;

import com.college.tree.Node;

public class TreeParser {
  private int parseIndex = 0;

  public Node parseNestedString(String data) {
    parseIndex = 0;
    return parseRecursive(data);
  }

  private Node parseRecursive(String data) {
    if (parseIndex >= data.length())
      return null;

    if (data.charAt(parseIndex) == '(') {
      parseIndex++;
    }

    if (data.charAt(parseIndex) == ')') {
      parseIndex++;
      return null;
    }

    StringBuilder sb = new StringBuilder();
    while (parseIndex < data.length() &&
        (Character.isDigit(data.charAt(parseIndex)) || data.charAt(parseIndex) == '-')) {
      sb.append(data.charAt(parseIndex));
      parseIndex++;
    }

    int value = Integer.parseInt(sb.toString());
    Node node = new Node(value);

    node.left = parseRecursive(data);
    node.right = parseRecursive(data);

    if (parseIndex < data.length() && data.charAt(parseIndex) == ')') {
      parseIndex++;

    }

    return node;
  }

  public String toNestedString(Node node) {
    if (node == null)
      return "()";
    return "(" + node.value + toNestedString(node.left) + toNestedString(node.right) + ")";
  }
}
