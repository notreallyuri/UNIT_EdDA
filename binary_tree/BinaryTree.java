public class BinaryTree {
  Node root;

  private Node insertRecursive(Node current, int data) {
    if (current == null) {
      return new Node(data);
    }

    if (data < current.data) {
      current.left = insertRecursive(current.left, data);
    } else if (data > current.data) {
      current.right = insertRecursive(current.right, data);
    }
    return current;
  }

  public void insert(int data) {
    root = insertRecursive(root, data);
  }
}
