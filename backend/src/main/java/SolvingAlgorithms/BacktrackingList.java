package SolvingAlgorithms;

import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BacktrackingList {
  // 2D array representing the sudoku board
  private ArrayList<ArrayList<Integer>> sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  /**
   * Constructor for the BacktrackingList class
   *
   * @param board     - 2D array representing the sudoku board
   * @param boardSize - length of the sudoku board
   */
  public BacktrackingList(ArrayList<ArrayList<Integer>> board, int boardSize) {
    this.sudokuBoard = board;
    this.boardSize = boardSize;
    this.blockSize = (int) Math.sqrt(boardSize);
  }

  /**
   * Getter method to return the sudoku board
   *
   * @return sudoku board as a 2D list of integers
   */
  public ArrayList<ArrayList<Integer>> getSudokuBoard() {
    return this.sudokuBoard;
  }

  /**
   * Solves the provided sudoku puzzle, filling in the empty values with the proper number
   *
   * @return true if puzzle solved, false if not
   */
  public void solve() {
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (this.sudokuBoard.get(row).get(column) == NO_VALUE) {
          for (int guess = 1; guess <= 9; guess++) {
            if (!inRow(row, guess) && !inColumn(column, guess) && !inBlock(row, column, guess)) {
              this.sudokuBoard.get(row).set(column, guess);
              solve();
              this.sudokuBoard.get(row).set(column, NO_VALUE);
            }
            return;
          }
        }
      }
    }
  }

  /**
   * Determines if the provided value is found in the corresponding row
   *
   * @param row   - row of the current square
   * @param value - value to determine if valid
   * @return true if not in the row, false if it is
   */
  public Boolean inRow(int row, int value) {
    return this.sudokuBoard.get(row).contains(value);
  }

  /**
   * Determines if the provided value is found in the corresponding column
   *
   * @param column - column of the current square
   * @param value  - value to determine if valid
   * @return true if not in the column, false if it is
   */
  public Boolean inColumn(int column, int value) {
    return this.sudokuBoard.stream()
               .map(row -> row.get(column))
               .collect(Collectors.toList())
               .contains(value);
  }

  /**
   * Determines if the provided value is found in the corresponding block
   *
   * @param row    - row of the current square
   * @param column - column of the current square
   * @param value  - value to determine if valid
   * @return true if not in the block, false if it is
   */
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
