import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class TreeApp {
  private BinaryTree tree = new BinaryTree();
  private Display display = new Display(tree);
  private JTextField input = new JTextField(8);
  private JButton btnInsert = new JButton("Inserir");

  public void init() {
    JFrame frame = new JFrame("Árvore Binária");

    JPanel pTop = new JPanel();
    pTop.add(new JLabel("Número:"));
    pTop.add(input);
    pTop.add(btnInsert);

    ActionListener addAction = e -> {
      try {
        int val = Integer.parseInt(input.getText());
        tree.insert(val);
        input.setText("");
        display.revalidate();
        display.repaint();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Número inválido");
      }
    };

    btnInsert.addActionListener(addAction);
    input.addActionListener(addAction);

    frame.setLayout(new BorderLayout());
    frame.add(pTop, BorderLayout.NORTH);
    frame.add(new JScrollPane(display), BorderLayout.CENTER);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
