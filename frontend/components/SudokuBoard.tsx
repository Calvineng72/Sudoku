import styles from './SudokuBoard.module.css'

export default function createGameEnvironment(): JSX.Element[] {
    let className: string = '';
    let html: JSX.Element[] = [];
  
    for (let i = 0; i < 81; i++) {
        if (((i % 9 == 0 || i % 9 == 1 || i % 9 == 2) && i < 21) ||
            ((i % 9 == 6 || i % 9 == 7 || i % 9 == 8) && i < 27) ||
            ((i % 9 == 3 || i % 9 == 4 || i % 9 == 5) && (i > 27 && i < 53)) ||
            ((i % 9 == 0 || i % 9 == 1 || i % 9 == 2) && i > 53) ||
            ((i % 9 == 6 || i % 9 == 7 || i % 9 == 8) && i > 53)) {
            className = styles.coloredSquare;
        }
        else {
            className = styles.square;
        }

        html.push(<input className={className} type="text" pattern="[0-9]"
            inputMode='numeric' maxLength={1}/>);
    }
    
    return html;
}  
