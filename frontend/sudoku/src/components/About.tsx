export default function About() {
    return (
        <div className="modal fade" id="About" tabIndex={-1} role="dialog" aria-labelledby="About" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="AboutTitle">Sudoku</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        This web-application displays sudoku puzzles created in a backend server for users to interact with.
                        You can select the size and difficulty of the puzzle. If you are stuck, you can click the check button,
                        which provides feedback on your current progress in the puzzle. 
                    </div>
                </div>
            </div>
        </div>
    );
}