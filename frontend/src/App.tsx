import './App.css';
import createGameEnvironment from './components/SudokuBoard';
import Header from './components/Header';
import SudokuSettings from './components/SudokuSettings';
import About from './components/About';

export default function App() {
  return (
    <div className='container'>
      <Header></Header>
      <div className='puzzle-container'>
        <div id='puzzle' className='puzzle'>
          {createGameEnvironment().map((square) => square)}
        </div>
      </div>
      <button type="button" className='btn btn-outline gameButton' onClick={createPuzzle}>Create Puzzle</button>
      <button type="button" className='btn btn-outline gameButton' onClick={check}>Check</button>
      <button type="button" className='btn btn-outline gameButton' onClick={solve}>Solve</button>
      <button type="button" className='btn btn-outline gameButton' onClick={clear}>Clear</button>
      <SudokuSettings></SudokuSettings>
      <About></About>
    </div>
  )
}

let solution: number[][] = [];
let puzzle: number[][] = [];

async function fetchPuzzle(): Promise<void> {
  await fetch('http://localhost:8080/getSudoku?boardSize=9')
    .then((r) => r.json())
    .then((json) => {
      solution = json.solution;
      puzzle = json.sudoku;
    });
}

async function createPuzzle() {
  clear();
  await fetchPuzzle();
  const inputs = document.querySelectorAll('input');
  puzzle.forEach((row, rowIndex) => {
    row.forEach((value, colIndex) => {
      const index = rowIndex * 9 + colIndex;
      const input = inputs[index];
      if (value !== 0) {
        input.value = value.toString();
        input.setAttribute('disabled', 'true')
      }
    });
  });
}

function check() {
  const inputs = document.querySelectorAll('input');
  solution.forEach((row, rowIndex) => {
    row.forEach((value, colIndex) => {
      const index = rowIndex * 9 + colIndex;
      const input = inputs[index];
      if (parseInt(input.value) === value) {
        input.style.color = 'green';
        input.setAttribute('disabled', 'true');
      }
      else if (input.value !== '') {
        input.style.color = 'red';
      }
    });
  });
}

function solve() {
  const inputs = document.querySelectorAll('input');
  solution.forEach((row, rowIndex) => {
    row.forEach((value, colIndex) => {
      const index = rowIndex * 9 + colIndex;
      const input = inputs[index];
      input.value = value.toString();
      input.style.color = 'black';
      input.setAttribute('disabled', 'true');
    });
  });
}

function clear() {
  const inputs = document.querySelectorAll('input');
  inputs.forEach((input) => {
    input.value = ''
    input.style.color = 'black'
    input.removeAttribute('disabled');
  });
}