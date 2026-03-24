import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JPanel {
    BinaryTree tree;

    Display(BinaryTree tree) {
        this.tree = tree;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Node clicado = findNode(tree.root, e.getX(), e.getY());
                if (clicado != null) {
                    exibirInformacoes(clicado);
                }
            }
        });
    }

    private int getTreeDepth(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(getTreeDepth(n.left), getTreeDepth(n.right));
    }

    @Override
    public Dimension getPreferredSize() {
        if (tree.root == null) return new Dimension(800, 600);

        int leafGap = 40;
        int depth = getTreeDepth(tree.root);
        int height = Math.max(600, (depth * 80) + 100);
        int width = (int) (Math.pow(2, depth - 1) * leafGap);

        return new Dimension(Math.max(800, width), height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree.root == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int canvasWidth = Math.max(getWidth(), getPreferredSize().width);
        int centerX = canvasWidth / 2;
        int initialEsp = canvasWidth / 4;

        draw(g2, tree.root, centerX, 60, initialEsp);
    }

    // Desenha nós e salva coordenadas
    void draw(Graphics2D g, Node node, int x, int y, int esp) {
        if (node == null) return;

        int r = 20;

        node.x = x; // salva coordenada x
        node.y = y; // salva coordenada y

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

    Node findNode(Node node, int mouseX, int mouseY) {
        if (node == null) return null;
        int r = 20;
        double dist = Math.hypot(mouseX - node.x, mouseY - node.y);
        if (dist <= r) return node;

        Node encontrado = findNode(node.left, mouseX, mouseY);
        if (encontrado == null) encontrado = findNode(node.right, mouseX, mouseY);
        return encontrado;
    }

    void exibirInformacoes(Node node) {
        String info = "Valor: " + node.value +
        "\nAltura: " + Search.height(node) +
        "\nProfundidade: " + Search.depth(tree.root,node);
        JOptionPane.showMessageDialog(this, info, "Informações do nó", JOptionPane.INFORMATION_MESSAGE);
    }
}