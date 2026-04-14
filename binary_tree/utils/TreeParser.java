
class TreeParser {
  private int parseIndex = 0;

  public Node parseNestedString(String data) {
    parseIndex = 0;
    return parseRecursive(data);
  }
}
