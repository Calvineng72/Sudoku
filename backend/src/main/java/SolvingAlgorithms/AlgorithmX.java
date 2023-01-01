package SolvingAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmX {
  private Node root;
  private List<ColumnHeader> columns;
  private int[][] matrix;

  public AlgorithmX(int[][] matrix) {
    this.matrix = matrix;
    this.root = new Node(-1, -1);
    this.columns = new ArrayList<>();

    // creates ColumnHeader for each column in the matrix
    for (int columnIndex = 0; columnIndex < matrix[0].length; columnIndex++) {
      ColumnHeader column = new ColumnHeader(columnIndex);
      this.columns.add(column);
      linkRight(root, column);
    }
  }

  public void linkRight(Node a, Node b) {
    b.left = a;
    b.right = a.right;
    a.right.left = b;
    a.right = b;
  }

  public void linkDown(Node a, Node b) {
    b.up = a;
    b.down = a.down;
    a.down.up = b;
    a.down = b;
  }

  public void removeColumn(ColumnHeader col) {
    col.right.left = col.left;
    col.left.right = col.right;
    for (Node i = col.down; i != col; i = i.down) {
      for (Node j = i.right; j != i; j = j.right) {
        j.down.up = j.up;
        j.up.down = j.down;
//        j.col.size--;
      }
    }
  }
}
