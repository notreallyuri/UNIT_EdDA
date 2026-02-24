class Main {
  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree();

    // Root Node
    tree.insert(5);

    // Child Nodes
    tree.insert(3);
    tree.insert(7);
    tree.insert(20);

    System.out.println("Root: " + tree.root.data);
    System.out.println("Left child: " + tree.root.left.data);
    System.out.println("Right child: " + tree.root.right.data);
    System.out.println("Right child of right child: " + tree.root.right.right.data);
  }
}
