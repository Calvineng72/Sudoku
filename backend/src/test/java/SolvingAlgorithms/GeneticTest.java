package SolvingAlgorithms;

import static org.junit.jupiter.api.Assertions.*;

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
    GeneticAlgorithm sudoku = new GeneticAlgorithm(puzzle, 4);
    int[][] calculated = sudoku.solve();
    System.out.println(Arrays.deepToString(calculated));
    assertArrayEquals(solution, calculated);
  }

  @Test
  public void testBeginnerSudoku() {
    // Create a 9x9 Sudoku puzzle with multiple sub-squares
    int[][] puzzle = new int[][] {
        {0, 0, 3, 0, 0, 0, 2, 0, 0},
        {0, 6, 0, 9, 8, 0, 0, 4, 3},
        {4, 9, 0, 0, 3, 1, 0, 0, 6},
        {9, 0, 7, 0, 0, 0, 8, 6, 0},
        {0, 4, 0, 0, 9, 8, 0, 0, 0},
        {0, 0, 5, 4, 0, 7, 1, 0, 9},
        {6, 0, 0, 0, 0, 3, 9, 0, 5},
        {5, 0, 8, 1, 0, 0, 0, 7, 2},
        {2, 0, 9, 0, 5, 6, 0, 3, 8}
    };
    // Expected solution for the puzzle
    int[][] solution = new int[][] {
        {8, 1, 3, 5, 7, 4, 2, 9, 6},
        {7, 6, 2, 9, 8, 5, 0, 4, 3},
        {4, 9, 5, 6, 3, 1, 8, 7, 2},
        {9, 3, 7, 2, 4, 6, 8, 1, 5},
        {1, 4, 6, 3, 9, 8, 7, 5, 2},
        {8, 2, 5, 4, 1, 7, 3, 6, 9},
        {6, 7, 4, 8, 2, 3, 9, 5, 1},
        {5, 8, 1, 7, 6, 9, 4, 2, 3},
        {2, 5, 9, 1, 4, 6, 5, 3, 8}
    };
    GeneticAlgorithm sudoku = new GeneticAlgorithm(puzzle, 9);
    int[][] calculated = sudoku.solve();
    assertArrayEquals(solution, calculated);
  }

  @Test
  public void testComplexSudoku() throws InterruptedException {
    // Create a 9x9 Sudoku puzzle with multiple sub-squares
    int[][] puzzle = new int[][] {
        {7, 0, 0, 0, 0, 0, 0, 2, 0},
        {6, 5, 0, 3, 8, 0, 0, 1, 0},
        {0, 0, 4, 0, 0, 5, 6, 0, 0},
        {0, 0, 8, 1, 0, 7, 0, 4, 0},
        {0, 6, 0, 0, 9, 0, 0, 7, 0},
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
    GeneticAlgorithm sudoku = new GeneticAlgorithm(puzzle, 9);
    int[][] calculated = sudoku.solve();
    assertArrayEquals(solution, calculated);
  }
}
