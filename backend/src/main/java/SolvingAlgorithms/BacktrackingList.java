package SolvingAlgorithms;

import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BacktrackingList {
  private ArrayList<ArrayList<Integer>> sudokuBoard;
  private int boardSize;
  private int blockSize;

  public BacktrackingList(ArrayList<ArrayList<Integer>> board, int boardSize, int blockSize) {
    this.sudokuBoard = board;
    this.boardSize = boardSize;
    this.blockSize = blockSize;
  }

  public ArrayList<ArrayList<Integer>> getSudokuBoard() {
    return this.sudokuBoard;
  }

  public void solve() {
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (this.sudokuBoard.get(row).get(column) == 0) {
          for (int guess = 1; guess <= 9; guess++) {
            if (!inRow(row, guess) && !inColumn(column, guess) && !inBlock(row, column, guess)) {
              this.sudokuBoard.get(row).set(column, guess);
              solve();
              this.sudokuBoard.get(row).set(column, 0);
            }
            return;
          }
        }
      }
    }
  }

  public Boolean inRow(int row, int value) {
    return this.sudokuBoard.get(row).contains(value);
  }

  public Boolean inColumn(int column, int value) {
    return this.sudokuBoard.stream()
               .map(row -> row.get(column))
               .collect(Collectors.toList())
               .contains(value);
  }

  public Boolean inBlock(int row, int column, int value) {
    int rowStart = row - row % this.blockSize;
    int columnStart = column - column % this.blockSize;

    ArrayList<Integer> block = new ArrayList<>(Collections.emptyList());
    for (int i = rowStart; i < rowStart + this.blockSize; i++) {
      for (int j = columnStart; j < columnStart + this.blockSize; j++) {
        block.add(this.sudokuBoard.get(i).get(j));
      }
    }

    return block.contains(value);
  }
}
