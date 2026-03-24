import java.util.*;

public class BinaryTree {
  private int parseIndex = 0;
  Node root;

  public void clear() {
    root = null;
  }

  public int countNodes(Node node) {
    if (node == null)
      return 0;
    return 1 + countNodes(node.left) + countNodes(node.right);
  }

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

  private void collectPreOrder(Node node, List<Integer> values) {
    if (node == null)
      return;
    values.add(node.value);
    collectPreOrder(node.left, values);
    collectPreOrder(node.right, values);
  }

  public List<Integer> getAllValues() {
    List<Integer> values = new ArrayList<>();
    collectPreOrder(root, values);
    return values;
  }

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

  public String toNestedString(Node node) {
    if (node == null)
      return "()";
    return "(" + node.value + toNestedString(node.left) + toNestedString(node.right) + ")";
  }

  public int getHeight(Node node) {
    if (node == null)
      return -1;
    return 1 + Math.max(getHeight(node.left), getHeight(node.right));
  }

  public ArrayList<Integer> preOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    Search.NLR(root, list);
    return list;
  }

  public ArrayList<Integer> inOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    Search.LNR(root, list);
    return list;
  }

  public ArrayList<Integer> postOrder() {
    ArrayList<Integer> list = new ArrayList<>();
    Search.LRN(root, list);
    return list;
  }
}
