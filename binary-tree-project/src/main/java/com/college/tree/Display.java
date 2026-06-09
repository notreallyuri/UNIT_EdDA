package com.college.tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import com.college.tree.utils.TreeSearch;

public class Display extends JPanel {
  private final BinaryTree tree;
  private final Map<Node, Point> nodeHitboxes = new HashMap<>();

  public Display(BinaryTree tree) {
    this.tree = tree;

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Node clicked = findNode(e.getX(), e.getY());
        if (clicked != null) {
          showNodeInformation(clicked);
        }
      }
    });
  }

  private int getTreeDepth(Node n) {
    if (n == null)
      return 0;
    return 1 + Math.max(getTreeDepth(n.left), getTreeDepth(n.right));
  }

  @Override
  public Dimension getPreferredSize() {
    if (tree.root == null)
      return new Dimension(800, 600);

    int leafGap = 40;
    int depth = getTreeDepth(tree.root);
    int height = Math.max(600, (depth * 80) + 100);
    int width = (int) (Math.pow(2, depth - 1) * leafGap);

    return new Dimension(Math.max(800, width), height);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    nodeHitboxes.clear();

    if (tree.root == null)
      return;

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int canvasWidth = Math.max(getWidth(), getPreferredSize().width);
    int centerX = canvasWidth / 2;
    int initialEsp = canvasWidth / 4;

    draw(g2, tree.root, centerX, 60, initialEsp);
  }

  private void draw(Graphics2D g, Node node, int x, int y, int esp) {
    if (node == null)
      return;

    int size = 40;
    int halfSize = size / 2;

    nodeHitboxes.put(node, new Point(x, y));

    if (node.left != null) {
      int xe = x - esp, ye = y + 80;
      drawLineToSquare(g, x, y, xe, ye, halfSize);
      draw(g, node.left, xe, ye, esp / 2);
    }
    if (node.right != null) {
      int xd = x + esp, yd = y + 80;
      drawLineToSquare(g, x, y, xd, yd, halfSize);
      draw(g, node.right, xd, yd, esp / 2);
    }

    if (node.isHighlightTheme()) {
      g.setColor(new Color(220, 50, 50));
    } else if (node.isDarkTheme()) {
      g.setColor(Color.DARK_GRAY);
    } else {
      g.setColor(Color.WHITE);
    }

    int startX = x - halfSize;
    int startY = y - halfSize;
    g.fillRect(startX, startY, size, size);
    g.setColor(Color.BLACK);
    g.drawRect(startX, startY, size, size);

    String s = String.valueOf(node.value);
    FontMetrics fm = g.getFontMetrics();

    g.setColor((node.isHighlightTheme() || node.isDarkTheme()) ? Color.WHITE : Color.BLACK);
    g.drawString(s, x - fm.stringWidth(s) / 2, y + fm.getAscent() / 4);
  }

  private void drawLineToSquare(Graphics2D g, int x1, int y1, int x2, int y2, int halfSize) {
    int xi = x1;
    int yi = y1 + halfSize;
    int xf = x2;
    int yf = y2 - halfSize;
    g.drawLine(xi, yi, xf, yf);
  }

  private Node findNode(int mouseX, int mouseY) {
    int halfSize = 20;
    for (Map.Entry<Node, Point> entry : nodeHitboxes.entrySet()) {
      Point p = entry.getValue();

      if (mouseX >= p.x - halfSize && mouseX <= p.x + halfSize &&
          mouseY >= p.y - halfSize && mouseY <= p.y + halfSize) {
        return entry.getKey();
      }
    }
    return null;
  }

  private void showNodeInformation(Node node) {
    String info = "Valor: " + node.value +
        "\nNível: " + TreeSearch.depth(tree.root, node) +
        "\nAltura: " + TreeSearch.height(node) +
        "\nProfundidade: " + TreeSearch.depth(tree.root, node);
    JOptionPane.showMessageDialog(this, info, "Informações do Nó", JOptionPane.INFORMATION_MESSAGE);
  }
}
