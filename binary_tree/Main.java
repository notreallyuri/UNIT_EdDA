import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Binary Tree Input (Enter 'q' to stop)");

    while (true) {
      System.out.print("Enter a number: ");
      if (scanner.hasNextInt()) {
        int value = scanner.nextInt();
        tree.insert(value);
      } else {
        break;
      }
    }

    scanner.close();
  }
}
