package SudokuGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomizedBoard {
  // 2D array representing the sudoku board
  private final int[][] sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;

  /**
   * Constructor for the RandomizedBoard class
   *
   * @param boardSize - length of the sudoku board
   * @param blockSize - length of one of the sudoku board blocks
   */
  public RandomizedBoard(int boardSize, int blockSize) {
    // Initialize the sudoku board with the specified size
    this.sudokuBoard = new int[boardSize][boardSize];
    this.boardSize = boardSize;
    this.blockSize = blockSize;
  }

  /**
   * Returns the sudoku board
   *
   * @return the sudoku board as a 2D int array
   */
  public int[][] getSudokuBoard() {
    return this.sudokuBoard;
  }

  /**
   * Generates a random sudoku puzzle by filling in the sudoku board with valid values
   */
  public void generatePuzzle() {
    // Create a new random number generator
    Random random = new Random();

    // Iterate through each cell of the board
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        // Create a list of options (1 through 9) for the current cell
        List<Integer> options = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        // Keep trying new random numbers until a valid one is found or the list of options is empty
        while (!options.isEmpty()) {
          int randomNumber = options.remove(random.nextInt(options.size()));
          if (validPuzzle(row, column, randomNumber)) {
            this.sudokuBoard[row][column] = randomNumber;
            break;
          }
        }
      }
    }
  }

  /**
   * Determines if the given value is a valid choice for the given cell in the sudoku board
   *
   * @param row - row index of the cell
   * @param column - column index of the cell
   * @param value - value to be checked
   * @return true if the value is a valid choice for the cell, false otherwise
   */
  public boolean validPuzzle(int row, int column, int value) {
    // Iterate through each cell in the given row and check if the value appears
    for (int j = 0; j < this.boardSize; j++) {
      if (this.sudokuBoard[row][j] == value) {
        return false;
      }
    }

    // Iterate through each cell in the given column and check if the value appears
    for (int i = 0; i < this.boardSize; i++) {
      if (this.sudokuBoard[i][column] == value) {
        return false;
      }
    }

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
