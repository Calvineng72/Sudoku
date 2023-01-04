package SolvingAlgorithms;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import SudokuGenerators.RandomizedBoard;
import org.junit.jupiter.api.Test;

public class BacktrackingFuzzTest {
  @Test
  public void fuzzTest() {
    for (int i = 0; i < 15; i++) {
      RandomizedBoard randomPuzzle = new RandomizedBoard(9);
      randomPuzzle.generatePuzzle();
      int[][] solution = randomPuzzle.getSudokuBoard();
      randomPuzzle.removeValues();
      int[][] puzzle = randomPuzzle.getSudokuBoard();

      Backtracking sudoku = new Backtracking(puzzle, 9);
      sudoku.solve();
      assertArrayEquals(solution, sudoku.getSudokuBoard());
    }
  }
}
