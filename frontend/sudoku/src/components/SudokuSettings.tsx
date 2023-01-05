export default function SudokuSettings() {
    return (
        <div id='difficulty' className='difficulty'>
            <select className="form-select" aria-label="Select puzzle difficulty">
                <option selected defaultValue="9">Select difficulty</option>
                <option value="4">Small (4x4)</option>
                <option value="9">Medium (9x9)</option>
                <option value="16">Large (16x16)</option>
            </select>
        </div>
    );
}