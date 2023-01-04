package Server;

import SudokuGenerators.RandomizedBoard;
import com.squareup.moshi.Moshi;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class SudokuHandler implements Route {

  public SudokuHandler() {}

  @Override
  public Object handle(Request request, Response response) {
    RandomizedBoard puzzle = new RandomizedBoard(9);
    puzzle.generatePuzzle();
    int[][] solution = puzzle.getSudokuBoard();
    puzzle.removeValues();
    int[][] sudoku = puzzle.getSudokuBoard();

    // Returns a success response with the sudoku puzzle and its solution
    return sudokuSuccessResponse(sudoku, solution);
  }

  public Object sudokuSuccessResponse(int[][] sudoku, int[][] solution) {
    // Creates map with success response
    Map<String, int[][]> responses = new HashMap<>();
    responses.put("sudoku", sudoku);
    responses.put("solution", solution);

    // Serializes responses into JSON format
    Moshi moshi = new Moshi.Builder().build();
    return moshi.adapter(Map.class).toJson(responses);
  }
}
