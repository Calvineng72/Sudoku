import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom'
import App from './App';
import userEvent from '@testing-library/user-event';

// Setup to make sure the page gets rendered
beforeEach(() => {
  render(<App />);
});

describe('components are properly rendered on screen', () => {
  test('renders navigation bar', () => {
    const navBar = screen.getByRole('navigation');
    expect(navBar).toBeInTheDocument();
  });

  test('renders sudoku board inputs', () => {
    expect(screen.getAllByRole('textbox').length).toBe(81);
  });

  test('renders create puzzle button', () => {
    const createButton = screen.getByRole('button', {name: 'Create Puzzle'});
    expect(createButton).toBeInTheDocument();
  });

  test('renders check button', () => {
    const checkButton = screen.getByRole('button', {name: 'Check'});
    expect(checkButton).toBeInTheDocument();
  });

  test('renders solve button', () => {
    const solveButton = screen.getByRole('button', {name: 'Solve'});
    expect(solveButton).toBeInTheDocument();
  });

  test('renders clear button', () => {
    const clearButton = screen.getByRole('button', {name: 'Clear'});
    expect(clearButton).toBeInTheDocument();
  });

  test('renders difficulty selection', () => {
    const difficulty = screen.getByRole('combobox');
    expect(difficulty).toBeInTheDocument();
    expect(screen.getAllByRole('option').length).toBe(4);
  });

  test('renders difficulty selection with all options', () => {
    expect(screen.getAllByRole('option').length).toBe(4);
  });
});

describe('users may interact with the sudoku board', () => {
  test('users can type in values', () => {
    const createButton = screen.getByRole('button', {name: 'Create Puzzle'});
    userEvent.click(createButton);
    const solveButton = screen.getByRole('button', {name: 'Solve'});
    userEvent.click(solveButton);
    const sudokuSquare = screen.getAllByRole('textbox');
    expect(sudokuSquare[0].hasAttribute('disabled')).toBe();
  });
});