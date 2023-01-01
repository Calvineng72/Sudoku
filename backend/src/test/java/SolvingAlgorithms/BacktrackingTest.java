package SolvingAlgorithms;

import static org.junit.jupiter.api.Assertions.*;

import SudokuGenerators.RandomizedBoard;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Backtracking Sudoku solving algorithm.
 */
class BacktrackingTest {
  /**
   * Tests the solve() method of the Backtracking class on an empty Sudoku puzzle.
   */
  @Test
  public void testEmptySudoku() {
    // Create an empty 4x4 Sudoku puzzle
    int[][] puzzle = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    // Expected solution for the empty puzzle
    int[][] solution = new int[][]{
        {1, 2, 3, 4},
        {3, 4, 1, 2},
        {2, 1, 4, 3},
        {4, 3, 2, 1}
    };
    // Create a Backtracking object for the puzzle and the expected size of the puzzle's sub-squares
    // (4x2 in this case)
    Backtracking sudoku = new Backtracking(puzzle, 4, 2);
    // Solve the puzzle
    sudoku.solve();
    // Assert that the solved puzzle is equal to the expected solution
    assertArrayEquals(solution, sudoku.getSudokuBoard());
  }

  /**
   * Tests the solve() method of the Backtracking class on a basic Sudoku puzzle with a few known
   * values.
   */
  @Test
  public void testBasicSudoku() {
    // Create a 4x4 Sudoku puzzle with a few known values
    int[][] puzzle = new int[][]{
        {1, 2, 0, 4},
        {0, 4, 0, 0},
        {2, 0, 4, 0},
        {0, 0, 2, 3}
    };
    // Expected solution for the puzzle
    int[][] solution = new int[][]{
        {1, 2, 3, 4},
        {3, 4, 1, 2},
        {2, 3, 4, 1},
        {4, 1, 2, 3}
    };
    // Create a Backtracking object for the puzzle and the expected size of the puzzle's sub-squares
    // (4x2 in this case)
    Backtracking sudoku = new Backtracking(puzzle, 4, 2);
    // Solve the puzzle
    sudoku.solve();
    // Assert that the solved puzzle is equal to the expected solution
    assertArrayEquals(solution, sudoku.getSudokuBoard());
  }

  /**
   * Tests the solve() method of the Backtracking class on a complex Sudoku puzzle with multiple
   * sub-squares.
   */
  @Test
  public void testComplexSudoku() {
    // Create a 9x9 Sudoku puzzle with multiple sub-squares
    int[][] puzzle = new int[][] {
        {0, 0, 0, 0, 0, 0, 0, 2, 0},
        {6, 5, 0, 3, 8, 0, 0, 1, 0},
        {0, 0, 4, 0, 0, 5, 6, 0, 0},
        {0, 0, 8, 1, 0, 7, 0, 4, 0},
        {0, 6, 0, 0, 0, 0, 0, 7, 0},
        {0, 7, 0, 4, 0, 6, 9, 0, 0},
        {0, 0, 1, 8, 0, 0, 3, 0, 0},
        {0, 4, 0, 0, 7, 1, 0, 9, 2},
        {0, 2, 0, 0, 0, 0, 0, 0, 0}
    };
    // Expected solution for the puzzle
    int[][] solution = new int[][] {
        {7, 1, 3, 6, 4, 9, 5, 2, 8},
        {6, 5, 9, 3, 8, 2, 7, 1, 4},
        {2, 8, 4, 7, 1, 5, 6, 3, 9},
        {9, 3, 8, 1, 5, 7, 2, 4, 6},
        {4, 6, 5, 2, 9, 8, 1, 7, 3},
        {1, 7, 2, 4, 3, 6, 9, 8, 5},
        {5, 9, 1, 8, 2, 4, 3, 6, 7},
        {3, 4, 6, 5, 7, 1, 8, 9, 2},
        {8, 2, 7, 9, 6, 3, 4, 5, 1}
    };
    // Create a Backtracking object for the puzzle and the expected size of the puzzle's sub-squares
    // (9x3 in this case)
    Backtracking sudoku = new Backtracking(puzzle, 9, 3);
    // Solve the puzzle
    sudoku.solve();
    // Assert that the solved puzzle is equal to the expected solution
    assertArrayEquals(solution, sudoku.getSudokuBoard());
  }

  /**
   * Tests the Backtracking algorithm on a small randomly generated puzzle
   * (4x4 with 2x2 sub-squares).
   */
  @Test
  public void testOnSmallRandomlyGenerated() {
    // Create a new RandomizedBoard object with a board size of 4 and block size of 2
    RandomizedBoard randomPuzzle = new RandomizedBoard(4, 2);
    // Generate a new puzzle
    randomPuzzle.generatePuzzle();
    // Get the expected solution from the RandomizedBoard object
    int[][] solution = randomPuzzle.getSudokuBoard();
    // Remove values from the puzzle
    randomPuzzle.removeValues();
    // Get the modified puzzle from the RandomizedBoard object
    int[][] puzzle = randomPuzzle.getSudokuBoard();

    // Create a Backtracking object for the puzzle and the expected size of the puzzle's sub-squares
    // (4x2 in this case)
    Backtracking sudoku = new Backtracking(puzzle, 4, 2);
    // Solve the puzzle
    sudoku.solve();
    // Assert that the solved puzzle is equal to the expected solution
    assertArrayEquals(solution, sudoku.getSudokuBoard());
  }

  /**
   * Tests the Backtracking algorithm on a randomly generated puzzle (9x9 with 3x3 sub-squares).
   */
  @Test
  public void testOnRandomlyGenerated() {
    // Create a new RandomizedBoard object with a board size of 9 and block size of 3
    RandomizedBoard randomPuzzle = new RandomizedBoard(9, 3);
    // Generate a new puzzle
    randomPuzzle.generatePuzzle();
    // Get the expected solution from the RandomizedBoard object
    int[][] solution = randomPuzzle.getSudokuBoard();
    // Remove values from the puzzle
    randomPuzzle.removeValues();
    // Get the modified puzzle from the RandomizedBoard object
    int[][] puzzle = randomPuzzle.getSudokuBoard();

    // Create a Backtracking object for the puzzle and the expected size of the puzzle's sub-squares
    // (9x3 in this case)
    Backtracking sudoku = new Backtracking(puzzle, 9, 3);
    // Solve the puzzle
    sudoku.solve();
    // Assert that the solved puzzle is equal to the expected solution
    assertArrayEquals(solution, sudoku.getSudokuBoard());
  }
}
