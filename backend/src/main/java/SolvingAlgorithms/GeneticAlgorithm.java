package SolvingAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

  // List of integers representing the fitness of each individual
  private List<Integer> fitnesses;

  // Probability of a mutation occurring during the mutation step
  private double mutationRate;

  // Random number generator
  private final Random random;

  // Constant representing an empty cell in the sudoku board
  private static final int NO_VALUE = 0;

  // Number of individuals in the population
  private static final int POPULATION_SIZE = 500;

  // Number of individuals selected for tournament selection
  private static final int TOURNAMENT_SIZE = 100;

  // Initial mutation rate
  private static final double MUTATION_RATE = 0.03;

  // Number of elite individuals that move on to the next generation
  private static final int ELITE_SIZE = 250;

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
    this.fitnesses = new ArrayList<>(POPULATION_SIZE);
    this.random = new Random();
    this.mutationRate = MUTATION_RATE;
  }

  /**
   * Runs the genetic algorithm to solve the sudoku puzzle, stopping once the maximum number
   * of generations is reached
   *
   * @return The completed sudoku puzzle as a 2D int array
   */
  public int[][] solve() {
    // Initialize the population
    initializePopulation();

    // Calculate the fitness of each individual in the population
    calculateFitnesses();

    // Counter for the number of generations
    int generation = 1;

    // Keep track of the best individual and its fitness
    int[][] bestIndividual = null;
    int bestFitness = Integer.MIN_VALUE;

    // Run the genetic algorithm for a maximum of 200 generations
    while (generation < 100) {
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

      // Calculate the fitness of the new population
      calculateFitnesses();

      int maxFitnessIndex = this.fitnesses.indexOf(Collections.max(this.fitnesses));
      int[][] fittestIndividual = this.population.get(maxFitnessIndex);

      if (generation % 5 == 0) {
        updateMutationRate(fittestIndividual, bestFitness);
      }

      // Update the best individual if a fitter one is found
      if (this.fitnesses.get(maxFitnessIndex) > bestFitness) {
        bestFitness = this.fitnesses.get(maxFitnessIndex);
        bestIndividual = fittestIndividual;
      }

      // If the fittest individual has the maximum possible fitness, return it as the solution
      if (bestFitness == 0) {
        return bestIndividual;
      }

      generation++;
    }

    return bestIndividual;
  }

  private void initializePopulation() {
    this.population = IntStream.range(0, POPULATION_SIZE)
        .mapToObj(i -> createIndividual())
        .collect(Collectors.toList());
  }

  private void calculateFitnesses() {
    this.fitnesses = this.population.stream()
        .map(this::fitness)
        .collect(Collectors.toList());
  }

  /**
   * Creates an individual (sudoku board) with randomly placed values based upon the original
   * puzzle provided to the class, ensuring the right number of each number appears in the puzzle.
   *
   * @return The created individual (sudoku board) as a 2D int array
   */
  private int[][] createIndividual() {
    // Create a copy of the original sudoku board
    int[][] puzzle = Arrays.stream(this.sudokuBoard)
        .map(int[]::clone)
        .toArray(int[][]::new);

    // Create a HashMap to store the count of each number present in the puzzle
    List<Integer> missingNumbers = new ArrayList<>();
    for (int i = 1; i <= this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        missingNumbers.add(i);
      }
    }

    // Remove the numbers already present in the puzzle from the list
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        int value = puzzle[row][column];
        if (value != NO_VALUE) {
          missingNumbers.remove((Integer) value);
        }
      }
    }

    // Shuffle the list of missing numbers
    Collections.shuffle(missingNumbers);

    // Add the missing numbers to the puzzle
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (puzzle[row][column] == NO_VALUE) {
          puzzle[row][column] = missingNumbers.remove(0);
        }
      }
    }

    return puzzle;
  }

  /**
   * Calculates the fitness of an individual (sudoku board). The individual is penalized when
   * its cells do not match the constraints of the sudoku puzzle.
   *
   * @param individual - The individual (sudoku board) to be evaluated
   * @return The fitness of the individual as an int
   */
  private int fitness(int[][] individual) {
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

    // Store the count of each value in the sudoku board
    Map<Integer, Integer> counts = new HashMap<>();
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        int value = individual[row][column];
        // If the value already exists in the map, increment its count
        if (counts.containsKey(value)) {
          counts.put(value, counts.get(value) + 1);
        }
        // If the value does not exist in the map, add it with a count of 1
        else {
          counts.put(value, 1);
        }
      }
    }

    // Iterate through each value in the map
    for (int key : counts.keySet()) {
      int count = counts.getOrDefault(key, 0);
      // Decrement the fitness by the difference between the expected count and actual count
      fitness -= Math.abs(this.boardSize - count);
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
        .limit(ELITE_SIZE)
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
        .map(this::fitness)
        .toList();

    // Select an individual randomly from the population multiple times and return the
    // fittest individual from the selections
    OptionalInt fittestIndex = IntStream.range(0, TOURNAMENT_SIZE)
        .map(i -> this.random.nextInt(POPULATION_SIZE))
        .reduce((i1, i2) -> fitnesses.get(i1) > fitnesses.get(i2) ? i1 : i2);

    return this.population.get(fittestIndex.orElse(-1));
  }

  /**
   * Creates a new child individual (sudoku board) by performing crossover on the given parent
   * individuals.
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

  /**
   * Mutates the given individual (sudoku board) by randomly swapping two cells' values.
   *
   * @param individual The individual (sudoku board) to be mutated
   * @return The mutated individual (sudoku board)
   */
  private int[][] mutate(int[][] individual) {
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        if (this.sudokuBoard[row][column] == NO_VALUE && random.nextDouble() < MUTATION_RATE) {
          // Select a random cell to swap with
          int swapRow = random.nextInt(this.boardSize);
          int swapColumn = random.nextInt(this.boardSize);

          // Swap the values of the two cells
          int temp = individual[row][column];
          individual[row][column] = individual[swapRow][swapColumn];
          individual[swapRow][swapColumn] = temp;
        }
      }
    }

    return individual;
  }

  /**
   * Updates the mutation rate in order to discourage convergence to local optima.
   *
   * @param fittestIndividual The fittest individual in the current generation
   * @param bestFitness The best fitness achieved from all generations
   */
  private void updateMutationRate(int[][] fittestIndividual, int bestFitness) {
    // Calculate the current fitness of the fittest individual
    int fitness = fitness(fittestIndividual);

    // If the fitness is not improving, increase the mutation rate to encourage more exploration
    if (fitness <= bestFitness) {
      this.mutationRate *= 1.1;
    }
    // If the fitness is improving rapidly, decrease the mutation rate to encourage exploitation
    else if (fitness - bestFitness >= 2) {
      this.mutationRate *= 0.9;
    }

    // Clamp the mutation rate to within the range [0, 1]
    this.mutationRate = Math.max(0, Math.min(1, this.mutationRate));
  }
}

//  All attempts at creating alternative methods to improve the genetic algorithm
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
//  public int[][] mutate(int[][] individual) {
//    for (int row = 0; row < this.boardSize; row++) {
//      for (int column = 0; column < this.boardSize; column++) {
//        if (this.random.nextDouble() < this.mutationRate) {
//          // Mutate this cell
//          int rowStart = row / this.blockSize * this.blockSize;
//          int columnStart = column / this.blockSize * this.blockSize;
//
//          // Keep track of the values that are already used in the row, column, and block
//          Set<Integer> usedValues = new HashSet<>();
//          for (int i = 0; i < this.boardSize; i++) {
//            usedValues.add(individual[row][i]);
//            usedValues.add(individual[i][column]);
//          }
//          for (int i = rowStart; i < rowStart + this.blockSize; i++) {
//            for (int j = columnStart * this.blockSize; j < columnStart + this.blockSize; j++) {
//              usedValues.add(individual[i][j]);
//            }
//          }
//
//          // Choose a new value for the cell that is not already used in the row, column, or block
//          int newValue;
//          Set<Integer> set = IntStream.rangeClosed(1, this.boardSize)
//              .boxed()
//              .collect(Collectors.toSet());
//          set.removeAll(usedValues);
//          if (set.isEmpty()) {
//            newValue = random.nextInt(this.boardSize) + 1;
//          } else {
//            newValue = set.stream().findFirst().get();
//          }
//
//          // Set the value of the cell to the new value
//          individual[row][column] = newValue;
//        }
//      }
//    }
//    return individual;
//  }
//  private int[][] createIndividual() {
//    // Create a copy of the original sudoku board
//    int[][] puzzle = Arrays.stream(this.sudokuBoard)
//        .map(int[]::clone)
//        .toArray(int[][]::new);
//
//    // Fill empty cells with random values
//    for (int row = 0; row < this.boardSize; row++) {
//      for (int column = 0; column < this.boardSize; column++) {
//        if (puzzle[row][column] == NO_VALUE) {
//          puzzle[row][column] = random.nextInt(this.boardSize) + 1;
//        }
//      }
//    }
//
//    return puzzle;
//  }
//  private int[][] mutate(int[][] individual) {
//    for (int row = 0; row < this.boardSize; row++) {
//      for (int column = 0; column < this.boardSize; column++) {
//        if (this.sudokuBoard[row][column] == NO_VALUE && random.nextDouble() < MUTATION_RATE) {
//          int mutation = random.nextInt(this.boardSize) + 1;
//          individual[row][column] = mutation;
//        }
//      }
//    }
//
//    return individual;
//  }
//  private int[][] crossover(int[][] parent1, int[][] parent2) {
//    // Create a copy of the original sudoku board
//    int[][] child = Arrays.stream(this.sudokuBoard)
//        .map(int[]::clone)
//        .toArray(int[][]::new);
//
//    // Randomly select a crossover point
//    int crossoverRow = this.random.nextInt(this.boardSize);
//    int crossoverColumn = this.random.nextInt(this.boardSize);
//
//    // Fill the child with values from the parents according to the crossover point
//    for (int row = 0; row < this.boardSize; row++) {
//      for (int column = 0; column < this.boardSize; column++) {
//        if (this.sudokuBoard[row][column] == NO_VALUE) {
//          if (row < crossoverRow || (row == crossoverRow && column <= crossoverColumn)) {
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
