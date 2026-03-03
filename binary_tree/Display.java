import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
  BinaryTree tree;

  Display(BinaryTree tree) {
    this.tree = tree;
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    draw(g2, tree.root, getWidth() / 2, 60, getWidth() / 4);
  }

  void draw(Graphics2D g, Node node, int x, int y, int esp) {
    if (node == null)
      return;

    int r = 20;

    if (node.left != null) {
      int xe = x - esp, ye = y + 80;
      line(g, x, y, xe, ye, r);
      draw(g, node.left, xe, ye, esp / 2);
    }
    if (node.right != null) {
      int xd = x + esp, yd = y + 80;
      line(g, x, y, xd, yd, r);
      draw(g, node.right, xd, yd, esp / 2);
    }

    g.setColor(Color.WHITE);
    g.fillOval(x - r, y - r, r * 2, r * 2);
    g.setColor(Color.BLACK);
    g.drawOval(x - r, y - r, r * 2, r * 2);

    String s = String.valueOf(node.value);
    FontMetrics fm = g.getFontMetrics();
    g.drawString(s, x - fm.stringWidth(s) / 2, y + fm.getAscent() / 4);
  }

  void line(Graphics2D g, int x1, int y1, int x2, int y2, int r) {
    double ang = Math.atan2(y2 - y1, x2 - x1);
    int xi = (int) (x1 + r * Math.cos(ang));
    int yi = (int) (y1 + r * Math.sin(ang));
    int xf = (int) (x2 - r * Math.cos(ang));
    int yf = (int) (y2 - r * Math.sin(ang));
    g.drawLine(xi, yi, xf, yf);
  }

}
