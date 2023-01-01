package SolvingAlgorithms;

import static org.junit.jupiter.api.Assertions.*;

import SudokuGenerators.RandomizedBoard;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class GeneticTest {
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
    GeneticAlgorithm sudoku = new GeneticAlgorithm(puzzle, 4);
    int[][] calculated = sudoku.solve();
    System.out.println(Arrays.deepToString(calculated));
    // Assert that the solved puzzle is equal to the expected solution
    assertArrayEquals(solution, calculated);
  }
}
