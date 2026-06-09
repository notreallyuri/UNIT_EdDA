package com.college.tree.utils;

import com.college.tree.BinaryTree;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class TreeStorageService {
  private static final String TREE_FILE = "tree.txt";
  private static final String HISTORY_FILE = "tree_history.txt";

  public void saveTree(BinaryTree tree) throws IOException {
    try (PrintWriter out = new PrintWriter(new FileWriter(TREE_FILE));
        PrintWriter historyOut = new PrintWriter(new FileWriter(HISTORY_FILE))) {

      TreeParser parser = new TreeParser();
      String structure = parser.toNestedString(tree.root);
      out.print(structure);

      List<String> log = tree.getRotationLog();
      if (!log.isEmpty()) {
        historyOut.print(String.join("\n", log));
      } else {
        historyOut.print("Nenhuma rotação registrada ou árvore não balanceável.");
      }
    }
  }

  public void loadTree(BinaryTree tree) throws IOException {
    File file = new File(TREE_FILE);
    if (!file.exists()) {
      throw new FileNotFoundException("Arquivo não encontrado!");
    }

    try (Scanner scanner = new Scanner(file).useDelimiter("\\Z")) {
      if (scanner.hasNext()) {
        String content = scanner.next();
        TreeParser parser = new TreeParser();
        tree.clear();
        tree.root = parser.parseNestedString(content.trim());
      }
    }
  }
}
