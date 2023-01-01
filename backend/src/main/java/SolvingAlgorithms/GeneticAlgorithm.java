package SolvingAlgorithms;

public class GeneticAlgorithm {
  // 2D array representing the sudoku board
  private final int[][] sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  private static final int POPULATION_SIZE = 100;

  /**
   * Constructor for the GeneticAlgorithm class
   *
   * @param board     - 2D array representing the sudoku board
   * @param boardSize - length of the sudoku board
   * @param blockSize - length of one of the sudoku board blocks
   */
  public GeneticAlgorithm(int[][] board, int boardSize, int blockSize) {
    this.sudokuBoard = board;
    this.boardSize = boardSize;
    this.blockSize = blockSize;
  }

  public int fitness(int[][] sudoku) {
    int fitness = 0;
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        int value = this.sudokuBoard[row][column];
        if (notInRow(row, column, value)) {
          fitness++;
        } else {
          fitness--;
        }
        if (notInColumn(row, column, value)) {
          fitness++;
        } else {
          fitness--;
        }
        if (notInBlock(row, column, value)) {
          fitness++;
        } else {
          fitness--;
        }
      }
    }
    return fitness;
  }

  /**
   * Determines if the provided value is found in the corresponding row
   *
   * @param row   - row of the current square
   * @param value - value to determine if valid
   * @return true if not in the row, false if it is
   */
  public boolean notInRow(int row, int column, int value) {
    // Iterate through each cell in the given row and check if the value appears
    for (int j = 0; j < this.boardSize; j++) {
      int current = this.sudokuBoard[row][j];
      if (current == value && j != column) {
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
  public boolean notInColumn(int row, int column, int value) {
    // Iterate through each cell in the given column and check if the value appears
    for (int i = 0; i < this.boardSize; i++) {
      int current = this.sudokuBoard[i][column];
      if (current == value && i != row) {
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
        int current = this.sudokuBoard[i][j];
        if (current == value && row != i && column != j) {
          return false;
        }
      }
    }

    return true;
  }
}
