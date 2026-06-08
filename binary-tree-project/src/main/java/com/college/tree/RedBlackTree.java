package com.college.tree;

import java.util.*;

import javax.swing.JOptionPane;

public class RedBlackTree extends BinaryTree {
  private RedBlackNode rootRB;
  private List<String> rotationLog = new ArrayList<>();

  @Override
  public void insert(int value) {
    if (containsNode(root, value)) {
      JOptionPane.showMessageDialog(null,
          "O valor " + value + " já existe na árvore.",
          "Aviso", JOptionPane.WARNING_MESSAGE);
      return;
    }

    RedBlackNode newNode = new RedBlackNode(value);
    rootRB = bstInsert(rootRB, newNode);
    fixViolation(newNode);

    root = rootRB;
  }

  @Override
  public List<String> getRotationLog() {
    return rotationLog;
  }

  @Override
  public void clearRotationLog() {
    rotationLog.clear();
  }

  private RedBlackNode bstInsert(RedBlackNode root, RedBlackNode pt) {
    if (root == null)
      return pt;

    RedBlackNode current = root;
    RedBlackNode parent = null;

    while (current != null) {
      parent = current;
      if (pt.value < current.value) {
        current = (RedBlackNode) current.left;
      } else {
        current = (RedBlackNode) current.right;
      }
    }

    pt.parent = parent;
    if (pt.value < parent.value) {
      parent.left = pt;
    } else {
      parent.right = pt;
    }

    return root;
  }

  private void fixViolation(RedBlackNode pt) {
    RedBlackNode parentPt = null;
    RedBlackNode grandParentPt = null;

    while (pt != rootRB && pt.color != RedBlackNode.BLACK && pt.parent.color == RedBlackNode.RED) {
      parentPt = pt.parent;
      grandParentPt = pt.parent.parent;

      if (parentPt == grandParentPt.left) {
        RedBlackNode unclePt = (RedBlackNode) grandParentPt.right;

        if (unclePt != null && unclePt.color == RedBlackNode.RED) {
          grandParentPt.color = RedBlackNode.RED;
          parentPt.color = RedBlackNode.BLACK;
          unclePt.color = RedBlackNode.BLACK;
          pt = grandParentPt;
        } else {
          if (pt == parentPt.right) {
            rotateLeft(parentPt);
            pt = parentPt;
            parentPt = pt.parent;
          }
          rotateRight(grandParentPt);
          boolean temp = parentPt.color;
          parentPt.color = grandParentPt.color;
          grandParentPt.color = temp;
          pt = parentPt;
        }
      } else {
        RedBlackNode unclePt = (RedBlackNode) grandParentPt.left;

        if (unclePt != null && unclePt.color == RedBlackNode.RED) {
          grandParentPt.color = RedBlackNode.RED;
          parentPt.color = RedBlackNode.BLACK;
          unclePt.color = RedBlackNode.BLACK;
          pt = grandParentPt;
        } else {
          if (pt == parentPt.left) {
            rotateRight(parentPt);
            pt = parentPt;
            parentPt = pt.parent;
          }
          rotateLeft(grandParentPt);
          boolean temp = parentPt.color;
          parentPt.color = grandParentPt.color;
          grandParentPt.color = temp;
          pt = parentPt;
        }
      }
    }
    rootRB.color = RedBlackNode.BLACK;
  }

  private void rotateLeft(RedBlackNode pt) {
    RedBlackNode ptRight = (RedBlackNode) pt.right;
    pt.right = ptRight.left;

    if (pt.right != null) {
      ((RedBlackNode) pt.right).parent = pt;
    }

    ptRight.parent = pt.parent;

    if (pt.parent == null) {
      rootRB = ptRight;
    } else if (pt == pt.parent.left) {
      pt.parent.left = ptRight;
    } else {
      pt.parent.right = ptRight;
    }

    ptRight.left = pt;
    pt.parent = ptRight;

    rotationLog.add("Rotação esquerda no nó " + pt.value);
  }

  private void rotateRight(RedBlackNode pt) {
    RedBlackNode ptLeft = (RedBlackNode) pt.left;
    pt.left = ptLeft.right;

    if (pt.left != null) {
      ((RedBlackNode) pt.left).parent = pt;
    }

    ptLeft.parent = pt.parent;

    if (pt.parent == null) {
      rootRB = ptLeft;
    } else if (pt == pt.parent.left) {
      pt.parent.left = ptLeft;
    } else {
      pt.parent.right = ptLeft;
    }

    ptLeft.right = pt;
    pt.parent = ptLeft;

    rotationLog.add("Rotação direita no nó " + pt.value);
  }

  @Override
  public String getTreeType() {
    return "Árvore Rubro-Negra";
  }

  @Override
  public void clear() {
    super.clear();
    rootRB = null;
  }
}
