public class BinaryTree {
  Node root;

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
      System.out.println("Value " + value + " already exists in the tree.");
    }
  }
}
