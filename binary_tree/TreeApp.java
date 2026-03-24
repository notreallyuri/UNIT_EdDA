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
  private JButton btnSearch = new JButton("Buscar");
  private JButton btnInformation = new JButton("Informações");

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
    pBottom.add(btnSearch);
    pBottom.add(btnInformation);

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
        String structure = tree.toNestedString(tree.root);
        out.print(structure);
        JOptionPane.showMessageDialog(frame, "Árvore salva: " + structure);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, "Erro ao salvar arquivo");
      }
    });

    btnLoad.addActionListener(e -> {
      File file = new File("tree.txt");
      if (!file.exists()) {
        JOptionPane.showMessageDialog(frame, "Arquivo não encontrado!");
        return;
      }

      try (Scanner scanner = new Scanner(file).useDelimiter("\\Z")) {
        if (scanner.hasNext()) {
          String content = scanner.next();

          tree.clear();
          tree.root = tree.parseNestedString(content.trim());

          display.revalidate();
          display.repaint();
          JOptionPane.showMessageDialog(frame, "Árvore carregada e reconstruída!");
        }
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro ao ler arquivo: " + ex.getMessage());
      }
    });

    btnInsert.addActionListener(insertAction);
    input.addActionListener(insertAction);

    btnSearch.addActionListener(e -> {
      JDialog dialog = new JDialog(frame, "Percursos", true);
      dialog.setLayout(new GridLayout(3, 1));

      JButton btnInOrder = new JButton("In-Order");
      JButton btnPreOrder = new JButton("Pre-Order");
      JButton btnPostOrder = new JButton("Post-Order");

      btnInOrder.addActionListener(ev -> JOptionPane.showMessageDialog(dialog, tree.inOrder().toString()));

      btnPreOrder.addActionListener(ev -> JOptionPane.showMessageDialog(dialog, tree.preOrder().toString()));

      btnPostOrder.addActionListener(ev -> JOptionPane.showMessageDialog(dialog, tree.postOrder().toString()));

      dialog.add(btnInOrder);
      dialog.add(btnPreOrder);
      dialog.add(btnPostOrder);

      dialog.setSize(200, 150);
      dialog.setLocationRelativeTo(frame);
      dialog.setVisible(true);
    });

    btnInformation.addActionListener(e -> {
      int height = tree.getHeight(tree.root);
      int nodes = tree.countNodes(tree.root);

      String info = "Altura da Árvore: " + height + "\n" +
          "Profundidade Máxima: " + height + "\n" +
          "Nível Máximo: " + (height + 1) + "\n" +
          "Total de Nós: " + nodes;

      JOptionPane.showMessageDialog(frame, info, "Informações da Árvore", JOptionPane.INFORMATION_MESSAGE);
    });

    frame.add(pTop, BorderLayout.NORTH);
    frame.add(new JScrollPane(display), BorderLayout.CENTER);
    frame.add(pBottom, BorderLayout.SOUTH);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
