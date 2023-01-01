package SolvingAlgorithms;

public class Node {
  int row, column;
  Node left, right, up, down;

  public Node(int row, int column) {
    this.row = row;
    this.column = column;
    this.left = this.right = this.up = this.down = this;
  }
}
