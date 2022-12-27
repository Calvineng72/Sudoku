import styles from './SudokuBoard.module.css'

export default function createGameEnvironment(squares: number) {
    const sudokuBoard = document.querySelector('#puzzle');

    for (let i = 0; i < squares; i++) {
        const inputElement = document.createElement("input");

        inputElement.setAttribute('class', styles.square);
        inputElement.setAttribute('type', 'text');
        inputElement.setAttribute('pattern', '[0-9]');
        inputElement.setAttribute('inputMode', 'numeric');
        inputElement.setAttribute('maxLength', '1');

        if (
            ((i % 9 == 0 || i % 9 == 1 || i % 9 == 2) && i < 21) ||
            ((i % 9 == 6 || i % 9 == 7 || i % 9 == 8) && i < 27) ||
            ((i % 9 == 3 || i % 9 == 4 || i % 9 == 5) && (i > 27 && i < 53)) ||
            ((i % 9 == 0 || i % 9 == 1 || i % 9 == 2) && i > 53) ||
            ((i % 9 == 6 || i % 9 == 7 || i % 9 == 8) && i > 53)) {
            inputElement.classList.add(styles.oddSection);
        }

        sudokuBoard?.appendChild(inputElement);
    }
}
