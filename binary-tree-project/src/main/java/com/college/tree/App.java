package com.college.tree;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import com.college.tree.utils.*;

import java.awt.*;
import java.util.List;

public class App {

  private BinaryTree tree = new BinaryTree();
  private Display display = new Display(tree);
  private JTextField input = new JTextField(8);

  private JButton btnInsert = new JButton("Inserir");
  private JButton btnClear = new JButton("Limpar");
  private JButton btnLoad = new JButton("Carregar");
  private JButton btnSave = new JButton("Salvar");
  private JButton btnSearch = new JButton("Percurso");
  private JButton btnPath = new JButton("Caminho");
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
    pBottom.add(btnPath);
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
        TreeParser parser = new TreeParser();
        String structure = parser.toNestedString(tree.root);
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
          TreeParser parser = new TreeParser();

          tree.clear();
          tree.root = parser.parseNestedString(content.trim());

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
      JButton btnClose = new JButton("Fechar");

      btnInOrder.addActionListener(ev -> JOptionPane.showMessageDialog(dialog, tree.inOrder().toString()));

      btnPreOrder.addActionListener(ev -> JOptionPane.showMessageDialog(dialog, tree.preOrder().toString()));

      btnPostOrder.addActionListener(ev -> JOptionPane.showMessageDialog(dialog, tree.postOrder().toString()));

      btnClose.addActionListener(ev -> dialog.dispose());

      dialog.add(btnInOrder);
      dialog.add(btnPreOrder);
      dialog.add(btnPostOrder);
      dialog.add(btnClose);

      dialog.setSize(200, 150);
      dialog.setLocationRelativeTo(frame);
      dialog.setVisible(true);
    });

    btnInformation.addActionListener(e -> {
      int height = TreeAnalyzer.getHeight(tree.root);
      int nodes = TreeAnalyzer.countNodes(tree.root);
      String type = tree.getTreeType();

      String info = "Tipo da Árvore: " + type +
          "\nAltura da Árvore: " + height +
          "\nProfundidade Máxima: " + height +
          "\nNível Máximo: " + (height + 1) +
          "\nTotal de Nós: " + nodes;

      JOptionPane.showMessageDialog(frame, info, "Informações da Árvore", JOptionPane.INFORMATION_MESSAGE);
    });

    btnPath.addActionListener(e -> {
      String inputValue = JOptionPane.showInputDialog(frame, "Digite o valor do nó:");

      if (inputValue == null)
        return;

      try {
        int val = Integer.parseInt(inputValue);

        List<Integer> path = TreeSearch.getPathTo(tree.root, val);

        if (path.isEmpty()) {
          JOptionPane.showMessageDialog(frame, "Valor não encontrado!");
        } else {
          JOptionPane.showMessageDialog(frame, "Caminho: " + path.toString());
        }

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Número inválido!");
      }
    });

    frame.add(pTop, BorderLayout.NORTH);
    frame.add(new JScrollPane(display), BorderLayout.CENTER);
    frame.add(pBottom, BorderLayout.SOUTH);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      App app = new App();
      app.init();
    });
  }
}
