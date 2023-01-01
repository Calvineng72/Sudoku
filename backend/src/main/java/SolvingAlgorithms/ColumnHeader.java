package SolvingAlgorithms;

public class ColumnHeader extends Node {
  int size;

  public ColumnHeader(int col) {
    super(-1, col);
    this.size = 0;
  }

  public int getSize() {
    return size;
  }
}
