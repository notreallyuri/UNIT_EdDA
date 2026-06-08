package com.college.tree;

public class RedBlackNode extends Node {
  public static final boolean RED = true;
  public static final boolean BLACK = false;

  public boolean color;
  public RedBlackNode parent;

  public RedBlackNode(int value) {
    super(value);
    this.color = RED;
    this.parent = null;
  }
}
