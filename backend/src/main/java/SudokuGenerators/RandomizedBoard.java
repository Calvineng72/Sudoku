package SudokuGenerators;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomizedBoard {
  // 2D array representing the sudoku board
  private final int[][] sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  /**
   * Constructor for the RandomizedBoard class
   *
   * @param boardSize - length of the sudoku board
   * @param blockSize - length of one of the sudoku board blocks
   */
  public RandomizedBoard(int boardSize, int blockSize) {
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
    return Arrays.stream(this.sudokuBoard)
                 .map(int[]::clone)
                 .toArray(int[][]::new);
  }

  /**
   * Generates a random sudoku puzzle by filling in the sudoku board with valid values
   */
  public boolean generatePuzzle() {
    // Create a new random number generator
    Random random = new Random();

    // Iterate through each cell of the sudoku board
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        // If the current cell is empty, try filling it with a valid number
        if (this.sudokuBoard[row][column] == NO_VALUE) {
          List<Integer> options = IntStream.rangeClosed(1, this.boardSize)
              .boxed()
              .collect(Collectors.toList());
          // Keep trying new random numbers until a valid one is found or the list of options is empty
          while (!options.isEmpty()) {
            int randomNumber = options.remove(random.nextInt(options.size()));
            if (validPuzzle(row, column, randomNumber)) {
              this.sudokuBoard[row][column] = randomNumber;
              if (generatePuzzle()) {
                return true;
              }
              this.sudokuBoard[row][column] = NO_VALUE;
            }
            if (options.isEmpty()) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * Removes a random 2/3 of the values from the sudoku board by replacing them with 0.
   */
  public void removeValues() {
    // Create a new random number generator
    Random random = new Random();
    // Calculate the number of cells to remove from the board
    int cellsToRemove = (int) (this.boardSize * this.boardSize * 2.0 / 3.0);

    // Keep removing cells until the desired number has been reached
    while (cellsToRemove > 0) {
      // Generate random indices for a cell
      int row = random.nextInt(this.boardSize);
      int column = random.nextInt(this.boardSize);
      // If the value at the current cell is not 0, replace it with 0 and decrease the counter
      if (this.sudokuBoard[row][column] != 0) {
        this.sudokuBoard[row][column] = 0;
        cellsToRemove--;
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
