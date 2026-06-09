package com.college.tree;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

import com.college.tree.utils.*;

public class App {
  private final BinaryTree tree;
  private final TreeStorageService storageService;

  private final JFrame frame;
  private final Display display;
  private final JTextField inputField;

  public App() {
    this.storageService = new TreeStorageService();
    this.tree = selectTreeType();
    this.display = new Display(tree);
    this.inputField = new JTextField(8);
    this.frame = new JFrame(tree.getTreeType());
  }

  private BinaryTree selectTreeType() {
    String[] options = { "Árvore Binária", "Árvore AVL", "Árvore Red-Black" };
    int choice = JOptionPane.showOptionDialog(null, "Qual tipo de árvore deseja criar?", "Tipo de Árvore",
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

    if (choice == JOptionPane.CLOSED_OPTION) {
      System.exit(0);
    }

    if (choice == 1)
      return new AVLTree();
    if (choice == 2)
      return new RedBlackTree();

    return new BinaryTree();
  }

  private void setupUI() {
    JPanel pTop = new JPanel();
    pTop.add(new JLabel("Número:"));
    pTop.add(inputField);

    JButton btnInsert = new JButton("Inserir");
    btnInsert.addActionListener(e -> handleInsert());
    inputField.addActionListener(e -> handleInsert());
    pTop.add(btnInsert);

    JPanel pBottom = new JPanel();
    pBottom.add(createButton("Salvar", this::handleSave));
    pBottom.add(createButton("Carregar", this::handleLoad));
    pBottom.add(createButton("Limpar", this::handleClear));
    pBottom.add(createButton("Percurso", this::showTraversals));
    pBottom.add(createButton("Caminho", this::handleSearchPath));
    pBottom.add(createButton("Informações", this::showInformation));
    pBottom.add(createButton("Histórico", this::showHistory));

    frame.add(pTop, BorderLayout.NORTH);
    frame.add(new JScrollPane(display), BorderLayout.CENTER);
    frame.add(pBottom, BorderLayout.SOUTH);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private JButton createButton(String label, Runnable action) {
    JButton button = new JButton(label);
    button.addActionListener(e -> action.run());
    return button;
  }

  private void refreshDisplay() {
    display.revalidate();
    display.repaint();
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(frame, message, "Erro", JOptionPane.ERROR_MESSAGE);
  }

  private void handleInsert() {
    try {
      int val = Integer.parseInt(inputField.getText());
      tree.insert(val);
      inputField.setText("");
      refreshDisplay();
    } catch (NumberFormatException ex) {
      showError("Número inválido");
    }
  }

  private void handleClear() {
    tree.clear();
    tree.clearRotationLog();
    refreshDisplay();
  }

  private void handleSave() {
    try {
      storageService.saveTree(tree);
      JOptionPane.showMessageDialog(frame, "Árvore salva em 'tree.txt' e histórico em 'tree_history.txt'!");
    } catch (IOException ex) {
      showError("Erro ao salvar arquivos: " + ex.getMessage());
    }
  }

  private void handleLoad() {
    try {
      storageService.loadTree(tree);
      refreshDisplay();
      JOptionPane.showMessageDialog(frame, "Árvore carregada e reconstruída!");
    } catch (FileNotFoundException ex) {
      showError(ex.getMessage());
    } catch (Exception ex) {
      showError("Erro ao ler arquivo: " + ex.getMessage());
    }
  }

  private void showHistory() {
    List<String> log = tree.getRotationLog();
    if (log.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Nenhuma rotação registrada para esta árvore.", "Histórico",
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    JTextArea textArea = new JTextArea(15, 30);
    textArea.setText(String.join("\n", log));
    textArea.setEditable(false);
    textArea.setMargin(new Insets(5, 5, 5, 5));
    JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Histórico de Rotações", JOptionPane.PLAIN_MESSAGE);
  }

  private void showInformation() {
    int height = TreeAnalyzer.getHeight(tree.root);
    int nodes = TreeAnalyzer.countNodes(tree.root);

    StringBuilder info = new StringBuilder();
    info.append("Tipo da Árvore: ").append(tree.getTreeType())
        .append("\nAltura da Árvore: ").append(height)
        .append("\nProfundidade Máxima: ").append(height)
        .append("\nNível Máximo: ").append(height + 1)
        .append("\nTotal de Nós: ").append(nodes);

    if (tree instanceof AVLTree) {
      List<String> log = tree.getRotationLog();
      String historico = log.isEmpty() ? "Nenhuma rotação realizada." : String.join("\n", log);
      info.append("\n\nHistórico de Rotações:\n").append(historico);
    }

    JOptionPane.showMessageDialog(frame, info.toString(), "Informações da Árvore", JOptionPane.INFORMATION_MESSAGE);
  }

  private void handleSearchPath() {
    String inputValue = JOptionPane.showInputDialog(frame, "Digite o valor do nó:");
    if (inputValue == null || inputValue.trim().isEmpty())
      return;

    try {
      int val = Integer.parseInt(inputValue);
      List<Integer> path = TreeSearch.getPathTo(tree.root, val);

      if (path.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Valor não encontrado!");
      } else {
        JOptionPane.showMessageDialog(frame, "Caminho: " + path);
      }
    } catch (NumberFormatException ex) {
      showError("Número inválido!");
    }
  }

  private void showTraversals() {
    JDialog dialog = new JDialog(frame, "Percursos", true);
    dialog.setLayout(new GridLayout(4, 1));

    dialog.add(createButton("In-Order", () -> JOptionPane.showMessageDialog(dialog, tree.inOrder().toString())));
    dialog.add(createButton("Pre-Order", () -> JOptionPane.showMessageDialog(dialog, tree.preOrder().toString())));
    dialog.add(createButton("Post-Order", () -> JOptionPane.showMessageDialog(dialog, tree.postOrder().toString())));
    dialog.add(createButton("Fechar", dialog::dispose));

    dialog.setSize(200, 180);
    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
  }

  public void init() {
    setupUI();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      App app = new App();
      app.init();
    });
  }
}
