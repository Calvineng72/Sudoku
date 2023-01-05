package Server;

import SudokuGenerators.RandomizedBoard;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

  @GetMapping("/getSudoku")
  public Map<String, int[][]> retrieveCoursesForStudent(@RequestParam int boardSize) {
    // Generate a randomized board and solve for its solution
    RandomizedBoard puzzle = new RandomizedBoard(boardSize);
    puzzle.generatePuzzle();
    int[][] solution = puzzle.getSudokuBoard();
    puzzle.removeValues();
    int[][] sudoku = puzzle.getSudokuBoard();

    // Create map with responses
    Map<String, int[][]> responses = new HashMap<>();
    responses.put("sudoku", sudoku);
    responses.put("solution", solution);

    return responses;
  }
}
