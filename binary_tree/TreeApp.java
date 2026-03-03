import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class TreeApp {
  private BinaryTree tree = new BinaryTree();
  private Display display = new Display(tree);
  private JTextField input = new JTextField(8);

  private JButton btnInsert = new JButton("Inserir");
  private JButton btnClear = new JButton("Limpar");
  private JButton btnLoad = new JButton("Carregar");
  private JButton btnSave = new JButton("Salvar");

  public void init() {
    JFrame frame = new JFrame("Árvore Binária");
    JPanel pTop = new JPanel();
    JPanel pBottom = new JPanel();

    pTop.add(new JLabel("Número:"));
    pTop.add(input);
    pTop.add(btnInsert);

    pBottom.add(btnSave);
    pBottom.add(btnLoad);
    pBottom.add(btnClear);

    ActionListener insertAction = e -> {
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

    btnClear.addActionListener(e -> {
      tree.clear();
      display.repaint();
      display.revalidate();
    });

    btnSave.addActionListener(e -> {
      try (PrintWriter out = new PrintWriter(new FileWriter("tree.txt"))) {
        for (int val : tree.getAllValues()) {
          out.println(val);
        }
        JOptionPane.showMessageDialog(frame, "Tree saved to tree.txt");
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, "Error saving file");
      }
    });

    btnLoad.addActionListener(e -> {
      File file = new File("tree.txt");
      if (!file.exists()) {
        JOptionPane.showMessageDialog(frame, "No save file found!");
        return;
      }
      try (Scanner fileScanner = new Scanner(file)) {
        tree.clear();
        while (fileScanner.hasNextInt()) {
          tree.insert(fileScanner.nextInt());
        }
        display.revalidate();
        display.repaint();
        JOptionPane.showMessageDialog(frame, "Tree loaded!");
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
    });

    btnInsert.addActionListener(insertAction);
    input.addActionListener(insertAction);

    frame.add(pTop, BorderLayout.NORTH);
    frame.add(new JScrollPane(display), BorderLayout.CENTER);
    frame.add(pBottom, BorderLayout.SOUTH);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
