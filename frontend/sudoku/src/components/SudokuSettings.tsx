export default function SudokuSettings() {
    return (
        <div id='difficulty' className='difficulty'>
            <select className="form-select" aria-label="Select puzzle difficulty" defaultValue="Easy">
                <option value="">Select difficulty</option>
                <option value="Easy">Easy</option>
                <option value="Medium">Medium</option>
                <option value="Hard">Hard</option>
            </select>
        </div>
    );
}