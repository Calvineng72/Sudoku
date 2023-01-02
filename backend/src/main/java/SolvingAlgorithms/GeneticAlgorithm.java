package SolvingAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing a genetic algorithm for solving a sudoku puzzle.
 */
public class GeneticAlgorithm {
  // 2D array representing the sudoku board
  private final int[][] sudokuBoard;

  // Size of the sudoku board (e.g. 9 for a 9x9 board)
  private final int boardSize;

  // Size of the blocks in the sudoku board (e.g. 3 for a 9x9 board with 3x3 blocks)
  private final int blockSize;

  // List of 2D arrays representing the current population of sudoku boards
  private List<int[][]> population;

  // Random number generator
  private final Random random;

  // Probability of a mutation occurring during the mutation step
  private double mutationRate;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  // Number of individuals in the population
  private static final int POPULATION_SIZE = 500;

  // Number of individuals selected for tournament selection
  private static final int TOURNAMENT_SIZE = 100;

  // Initial mutation rate
  private static final double MUTATION_RATE = 0.03;

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
    this.mutationRate = MUTATION_RATE;
  }

  /**
   * Method that runs the genetic algorithm to solve the sudoku puzzle.
   *
   * @return The completed sudoku puzzle as a 2D int array
   */
  public int[][] solve() {
    // Initialize the population
    this.population = IntStream.range(0, POPULATION_SIZE)
        .mapToObj(i -> createIndividual())
        .collect(Collectors.toList());

    // Calculate the fitness of each individual in the population
    List<Integer> fitnesses = population.stream()
        .map(this::calculateFitness)
        .collect(Collectors.toList());

    // Counter for the number of generations
    int generation = 1;

    // Keep track of the best individual and its fitness
    int[][] bestIndividual = null;
    int bestFitness = Integer.MIN_VALUE;

    // Run the genetic algorithm for a maximum of 200 generations
    while (generation < 200) {
      // Select the fittest individuals for elitism
      List<int[][]> fittestIndividuals = selectFittestIndividuals(fitnesses);

      // Generate new individuals to fill the remaining slots in the population
      while (fittestIndividuals.size() < POPULATION_SIZE) {
        int[][] parent1 = selection();
        int[][] parent2 = selection();
        fittestIndividuals.add(crossover(parent1, parent2));
      }

      this.population = fittestIndividuals;

      // Mutate each individual in the population
      this.population.replaceAll(this::mutate);

      System.out.println(fitnesses);
      System.out.println(Collections.max(fitnesses));
      System.out.println(generation);

      // Calculate the fitness of the new population
      fitnesses = population.stream()
          .map(this::calculateFitness)
          .collect(Collectors.toList());

      int maxFitnessIndex = fitnesses.indexOf(Collections.max(fitnesses));
      int[][] fittestIndividual = population.get(maxFitnessIndex);

      // Update the best individual if a fitter one is found
      if (fitnesses.get(maxFitnessIndex) > bestFitness) {
        bestFitness = fitnesses.get(maxFitnessIndex);
        bestIndividual = fittestIndividual;
      }

//      if (generation % 5 == 0) {
//        updateMutationRate(generation, bestIndividual, bestFitness);
//      }

      // If the fittest individual has the maximum possible fitness, return it as the solution
      if (bestFitness == 0) {
        return bestIndividual;
      }

      generation++;
    }

    return bestIndividual;
  }

  /**
   * Method that creates an individual (sudoku board) with randomly placed values.
   *
   * @return The created individual (sudoku board) as a 2D int array
   */
  private int[][] createIndividual() {
    // Create a copy of the original sudoku board
    int[][] puzzle = Arrays.stream(this.sudokuBoard)
                           .map(int[]::clone)
                           .toArray(int[][]::new);

    // Fill empty cells with random values
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (puzzle[row][column] == NO_VALUE) {
          puzzle[row][column] = random.nextInt(this.boardSize) + 1;
        }
      }
    }

    return puzzle;
  }

  /**
   * Method that calculates the fitness of an individual (sudoku board). The fitness is calculated
   * as the number of correctly placed values in the board.
   *
   * @param individual - The individual (sudoku board) to be evaluated
   * @return The fitness of the individual as an int
   */
  private int calculateFitness(int[][] individual) {
    // Initialize the fitness to zero
    int fitness = 0;

    // Check rows for correctly placed values
    for (int row = 0; row < this.boardSize; row++) {
      // Keep track of values used in this row
      Set<Integer> usedValues = new HashSet<>();
      for (int column = 0; column < this.boardSize; column++) {
        // If the value is already used in the row, decrement the fitness
        if (usedValues.contains(individual[row][column])) {
          fitness--;
        }
        usedValues.add(individual[row][column]);
      }
    }

    // Check columns for correctly placed values
    for (int column = 0; column < this.boardSize; column++) {
      // Keep track of values used in this column
      Set<Integer> usedValues = new HashSet<>();
      for (int row = 0; row < this.boardSize; row++) {
        // If the value is already used in the column, decrement the fitness
        if (usedValues.contains(individual[row][column])) {
          fitness--;
        }
        usedValues.add(individual[row][column]);
      }
    }

    // Check blocks for correctly placed values
    for (int row = 0; row < this.boardSize; row += this.blockSize) {
      for (int column = 0; column < this.boardSize; column += this.blockSize) {
        // Keep track of values used in this block
        Set<Integer> usedValues = new HashSet<>();
        for (int blockRow = row; blockRow < row + this.blockSize; blockRow++) {
          for (int blockCol = column; blockCol < column + this.blockSize; blockCol++) {
            // If the value is already used in the block, decrement the fitness
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

  /**
   * Selects the fittest individuals (sudoku boards) from the current population.
   *
   * @param fitnesses A list of fitness values for each individual in the population
   * @return A list of the fittest individuals (sudoku boards)
   */
  private List<int[][]> selectFittestIndividuals(List<Integer> fitnesses) {
    // Create a sorted list of indices to the original population
    // Sorting is done in descending order of fitness
    List<Integer> indices = IntStream.range(0, this.population.size())
        .boxed()
        .sorted((i1, i2) -> fitnesses.get(i2).compareTo(fitnesses.get(i1)))
        .toList();

    // Select the fittest individuals
    return indices.stream()
        .limit(100)
        .map(i -> this.population.get(i))
        .collect(Collectors.toList());
  }

  /**
   * Selects an individual (sudoku board) from the population using tournament selection.
   *
   * @return The selected individual (sudoku board)
   */
  private int[][] selection() {
    // Calculate the fitness of each individual in the population
    List<Integer> fitnesses = this.population.stream()
        .map(this::calculateFitness)
        .toList();

    // Select an individual randomly from the population multiple times and return the
    // fittest individual from the selections
    OptionalInt fittestIndex = IntStream.range(0, TOURNAMENT_SIZE)
        .map(i -> this.random.nextInt(POPULATION_SIZE))
        .reduce((i1, i2) -> fitnesses.get(i1) > fitnesses.get(i2) ? i1 : i2);

    return this.population.get(fittestIndex.orElse(-1));
  }


//  private int[][] selection() {
//    List<Integer> fitnesses = new ArrayList<>();
//    for (int[][] individual : this.population) {
//      fitnesses.add(calculateFitness(individual));
//    }
//
//    int minFitness = Collections.min(fitnesses);
//    for (int i = 0; i < fitnesses.size(); i++) {
//      fitnesses.set(i, fitnesses.get(i) + Math.abs(minFitness));
//    }
//
//    int totalFitness = 0;
//    for (int fitness : fitnesses) {
//      totalFitness += fitness;
//    }
//
//    int rand = random.nextInt(totalFitness);
//    int current = 0;
//    for (int i = 0; i < this.population.size(); i++) {
//      current += fitnesses.get(i);
//      if (current >= rand) {
//        int[][] parent = this.population.get(i);
//        return parent;
//      }
//    }
//
//    // This point should not be reached
//    return null;
//  }

  /**
   * Creates a new child individual (sudoku board) by performing crossover on the given parent individuals.
   *
   * @param parent1 The first parent individual (sudoku board)
   * @param parent2 The second parent individual (sudoku board)
   * @return The created child individual (sudoku board)
   */
  private int[][] crossover(int[][] parent1, int[][] parent2) {
    // Create a copy of the original sudoku board
    int[][] child = Arrays.stream(this.sudokuBoard)
                          .map(int[]::clone)
                          .toArray(int[][]::new);

    // Randomly select a crossover point
    int crossoverRow = this.random.nextInt(this.boardSize);
    int crossoverColumn = this.random.nextInt(this.boardSize);

    // Fill the child with values from the parents according to the crossover point
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (this.sudokuBoard[row][column] == NO_VALUE) {
          if (row < crossoverRow || (row == crossoverRow && column <= crossoverColumn)) {
            child[row][column] = parent1[row][column];
          } else {
            child[row][column] = parent2[row][column];
          }
        }
      }
    }

    return child;
  }

//  public int[][] crossover(int[][] parent1, int[][] parent2) {
//    int[][] child = Arrays.stream(this.sudokuBoard)
//                          .map(int[]::clone)
//                          .toArray(int[][]::new);
//
//    for (int row = 0; row < this.boardSize; row++) {
//      for (int column = 0; column < this.boardSize; column++) {
//        if (this.sudokuBoard[row][column] == NO_VALUE) {
//          boolean useParent1Gene = this.random.nextBoolean();
//          if (useParent1Gene) {
//            child[row][column] = parent1[row][column];
//          } else {
//            child[row][column] = parent2[row][column];
//          }
//        }
//      }
//    }
//
//    return child;
//  }

//  private int[][] crossover(int[][] parent1, int[][] parent2) {
//    int[][] child = new int[this.boardSize][this.boardSize];
//
//    int startRow = random.nextInt(this.boardSize);
//    int startColumn = random.nextInt(this.boardSize);
//    int endRow = startRow + random.nextInt(this.boardSize - startRow);
//    int endColumn = startColumn + random.nextInt(this.boardSize - startColumn);
//
//    for (int row = startRow; row <= endRow; row++) {
//      for (int column = startColumn; column <= endColumn; column++) {
//        if (this.sudokuBoard[row][column] == NO_VALUE) {
//          child[row][column] = parent2[row][column];
//        }
//      }
//    }
//
//    for (int row = 0; row < this.boardSize; row++) {
//      for (int column = 0; column < this.boardSize; column++) {
//        if (row < startRow || row > endRow || column < startColumn || column > endColumn) {
//          if (this.sudokuBoard[row][column] == NO_VALUE) {
//            child[row][column] = parent1[row][column];
//          }
//        }
//      }
//    }
//
//    return child;
//  }

  /**
   * Mutates the given individual (sudoku board) by randomly changing a cell's value.
   *
   * @param individual The individual (sudoku board) to be mutated
   * @return The mutated individual (sudoku board)
   */
  private int[][] mutate(int[][] individual) {
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (this.sudokuBoard[row][column] == NO_VALUE && random.nextDouble() < MUTATION_RATE) {
          int mutation = random.nextInt(this.boardSize) + 1;
          individual[row][column] = mutation;
        }
      }
    }
    
    return individual;
  }

//  private void updateMutationRate(int generation, int[][] fittestIndividual, int bestFitness) {
//    // Calculate the current fitness of the fittest individual
//    int fitness = calculateFitness(fittestIndividual);
//
//    // If the fitness is not improving, increase the mutation rate to encourage more exploration
//    if (fitness <= bestFitness) {
//      this.mutationRate *= 1.1;
//    }
//    // If the fitness is improving rapidly, decrease the mutation rate to encourage exploitation
//    else if (fitness - bestFitness >= 2) {
//      this.mutationRate *= 0.9;
//    }
//  }
}
