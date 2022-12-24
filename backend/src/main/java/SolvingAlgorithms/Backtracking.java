package SolvingAlgorithms;

public class Backtracking {
  // 2D array representing the sudoku board
  private final int[][] sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  /**
   * Constructor for the Backtracking class
   *
   * @param board     - 2D array representing the sudoku board
   * @param boardSize - length of the sudoku board
   * @param blockSize - length of one of the sudoku board blocks
   */
  public Backtracking(int[][] board, int boardSize, int blockSize) {
    this.sudokuBoard = board;
    this.boardSize = boardSize;
    this.blockSize = blockSize;
  }

  /**
   * Getter method to return the sudoku board
   *
   * @return sudoku board as a 2D int array
   */
  public int[][] getSudokuBoard() {
    return this.sudokuBoard;
  }

  /**
   * Solves the provided sudoku puzzle, filling in the empty values with the proper number
   *
   * @return true if puzzle solved, false if not
   */
  public boolean solve() {
    // Iterate through each cell of the sudoku board
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        // If the current cell is empty, try filling it with a valid number
        if (this.sudokuBoard[row][column] == NO_VALUE) {
          for (int guess = 1; guess <= this.boardSize; guess++) {
            // Check if the current guess is valid by checking if it appears in the same row,
            // column, or block as the current cell
            if (notInRow(row, guess) && notInColumn(column, guess) && notInBlock(row, column,
                guess)) {
              // If the guess is valid, fill the current cell with the guess and recursively solve
              // the rest of the puzzle
              this.sudokuBoard[row][column] = guess;
              if (solve()) {
                return true;
              }
              // If the guess leads to an invalid solution, reset the current cell to empty and
              // try the next guess
              this.sudokuBoard[row][column] = NO_VALUE;
            }
          }
          // If no valid guess is found, return false to trigger backtracking
          return false;
        }
      }
    }
    // If all cells have been filled and the puzzle is valid, return true
    return true;
  }

  /**
   * Determines if the provided value is found in the corresponding row
   *
   * @param row   - row of the current square
   * @param value - value to determine if valid
   * @return true if not in the row, false if it is
   */
  public boolean notInRow(int row, int value) {
    // Iterate through each cell in the given row and check if the value appears
    for (int column = 0; column < this.boardSize; column++) {
      if (this.sudokuBoard[row][column] == value) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if the provided value is found in the corresponding column
   *
   * @param column - column of the current square
   * @param value  - value to determine if valid
   * @return true if not in the column, false if it is
   */
  public boolean notInColumn(int column, int value) {
    // Iterate through each cell in the given column and check if the value appears
    for (int row = 0; row < this.boardSize; row++) {
      if (this.sudokuBoard[row][column] == value) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if the provided value is found in the corresponding block
   *
   * @param row    - row of the current square
   * @param column - column of the current square
   * @param value  - value to determine if valid
   * @return true if not in the block, false if it is
   */
  public boolean notInBlock(int row, int column, int value) {
    // Calculate the starting indices for the block that the current cell belongs to
    int rowStart = row / this.blockSize * this.blockSize;
    int columnStart = column / this.blockSize * this.blockSize;

    // Iterate through each cell in the block and check if the value appears
    for (int i = rowStart; i < rowStart + this.blockSize; i++) {
      for (int j = columnStart; j < columnStart + this.blockSize; j++) {
        if (this.sudokuBoard[i][j] == value) {
          return false;
        }
      }
    }

    return true;
  }
}
