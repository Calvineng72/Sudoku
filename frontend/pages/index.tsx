import styles from '../styles/Home.module.css'
import createGameEnvironment from '../components/SudokuBoard'

export default function Home() {  
  return (
    <div className={styles.container}>
      <div id='puzzle' className='puzzle'>
        {createGameEnvironment().map((square) => square)}
      </div>
      <button onClick={createPuzzle}>Create Puzzle</button>
      <button onClick={check}>Check</button>
      <button onClick={solve}>Solve</button>
      <button onClick={clear}>Clear</button>
    </div>
  )
}

let solution: number[][] = [];
let puzzle: number[][] = [];

async function fetchPuzzle(): Promise<void> {
  await fetch('http://localhost:3232/getSudoku')
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
