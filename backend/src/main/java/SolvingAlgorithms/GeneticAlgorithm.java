package SolvingAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
  // 2D array representing the sudoku board
  private final int[][] sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;
  private List<int[][]> population;

  private Random random;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  private static final int POPULATION_SIZE = 150;
  private static final int TOURNAMENT_SIZE = 30;
  private static final double MUTATION_RATE = 0.05;
  private static final int MAX_GENERATIONS = 500;

  /**
   * Constructor for the GeneticAlgorithm class
   *
   * @param board     - 2D array representing the sudoku board
   * @param boardSize - length of the sudoku board
   */
  public GeneticAlgorithm(int[][] board, int boardSize) {
    this.sudokuBoard = board;
    this.boardSize = boardSize;
    this.blockSize = (int) Math.sqrt(boardSize);
    this.population = new ArrayList<>(POPULATION_SIZE);
    this.random = new Random();
  }

  /**
   * Getter method to return the sudoku board
   *
   * @return sudoku board as a 2D int array
   */
  public int[][] getSudokuBoard() {
    return Arrays.stream(this.sudokuBoard)
        .map(int[]::clone)
        .toArray(int[][]::new);
  }

  public int[][] solve() {
    for (int i = 0; i < POPULATION_SIZE; i++) {
      this.population.add(createIndividual());
    }

    List<Integer> fitnesses = new ArrayList<>(POPULATION_SIZE);
    for (int[][] individual : this.population) {
      fitnesses.add(calculateFitness(individual));
    }

    System.out.println(fitnesses);

    int generation = 0;
    while (generation < MAX_GENERATIONS) {
      // Select the fittest individuals for elitism
      List<int[][]> fittestIndividuals = selectFittestIndividuals(fitnesses, 50);

      // Generate new individuals to fill the remaining slots in the population
      while (fittestIndividuals.size() < POPULATION_SIZE) {
        int[][] parent1 = selection();
        int[][] parent2 = selection();
        fittestIndividuals.add(mutate(crossover(parent1, parent2)));
      }

      population = fittestIndividuals;

      // Calculate the fitness of the new population
      fitnesses.clear();
      for (int[][] individual : population) {
        fitnesses.add(calculateFitness(individual));
      }

      System.out.println(fitnesses);

      generation++;
    }

    int fittestIndex = 0;
    for (int i = 1; i < POPULATION_SIZE; i++) {
      if (fitnesses.get(i) > fitnesses.get(fittestIndex)) {
        fittestIndex = i;
      }
    }
    return this.population.get(fittestIndex);
  }

  private int[][] createIndividual() {
    int[][] puzzle = Arrays.stream(this.sudokuBoard)
                           .map(int[]::clone)
                           .toArray(int[][]::new);
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (puzzle[row][column] == NO_VALUE) {
          puzzle[row][column] = random.nextInt(this.boardSize) + 1;
        }
      }
    }
    return puzzle;
  }

  private int calculateFitness(int[][] individual) {
    int fitness = 0;

    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (individual[row][column] == this.sudokuBoard[row][column]) {
          fitness++;
        }
      }
    }

    for (int row = 0; row < this.boardSize; row++) {
      Set<Integer> usedValues = new HashSet<>();
      for (int column = 0; column < this.boardSize; column++) {
        if (usedValues.contains(individual[row][column])) {
          fitness--;
        }
        usedValues.add(individual[row][column]);
      }
    }

    for (int column = 0; column < this.boardSize; column++) {
      Set<Integer> usedValues = new HashSet<>();
      for (int row = 0; row < this.boardSize; row++) {
        if (usedValues.contains(individual[row][column])) {
          fitness--;
        }
        usedValues.add(individual[row][column]);
      }
    }

    for (int row = 0; row < this.boardSize; row += this.blockSize) {
      for (int column = 0; column < this.boardSize; column += this.blockSize) {
        Set<Integer> usedValues = new HashSet<>();
        for (int blockRow = row; blockRow < row + this.blockSize; blockRow++) {
          for (int blockCol = column; blockCol < column + this.blockSize; blockCol++) {
            if (usedValues.contains(individual[blockRow][blockCol])) {
              fitness--;
            }
            usedValues.add(individual[blockRow][blockCol]);
          }
        }
      }
    }

    return fitness;
  }

  private List<int[][]> selectFittestIndividuals(List<Integer> fitnesses, int numFittest) {
    List<int[][]> fittestIndividuals = new ArrayList<>();
    for (int i = 0; i < numFittest; i++) {
      int fittestIndex = 0;
      for (int j = 1; j < fitnesses.size(); j++) {
        if (fitnesses.get(j) > fitnesses.get(fittestIndex)) {
          fittestIndex = j;
        }
      }
      fittestIndividuals.add(this.population.get(fittestIndex));
      fitnesses.remove(fittestIndex);
    }
    return fittestIndividuals;
  }

  private int[][] selection() {
    List<Integer> fitnesses = new ArrayList<>();
    for (int[][] individual : this.population) {
      fitnesses.add(calculateFitness(individual));
    }

    List<int[][]> subpopulation = new ArrayList<>(TOURNAMENT_SIZE);
    List<Integer> subpopulationFitness = new ArrayList<>(TOURNAMENT_SIZE);
    for (int i = 0; i < TOURNAMENT_SIZE; i++) {
      int index = random.nextInt(POPULATION_SIZE);
      subpopulation.add(this.population.get(index));
      subpopulationFitness.add(fitnesses.get(index));
    }

    int fittestIndex = 0;
    for (int i = 1; i < TOURNAMENT_SIZE; i++) {
      if (subpopulationFitness.get(i) > subpopulationFitness.get(fittestIndex)) {
        fittestIndex = i;
      }
    }
    return subpopulation.get(fittestIndex);
  }

  private int[][] crossover(int[][] parent1, int[][] parent2) {
    int[][] child = new int[this.boardSize][this.boardSize];
    boolean parent = random.nextBoolean();

    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (parent) {
          child[row][column] = parent1[row][column];
        } else {
          child[row][column] = parent2[row][column];
        }
      }
    }

    return child;
  }

  private int[][] mutate(int[][] individual) {
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (random.nextDouble() < MUTATION_RATE) {
          int mutation = random.nextInt(this.boardSize) + 1;
          individual[row][column] = mutation;
        }
      }
    }
    return individual;
  }
}
