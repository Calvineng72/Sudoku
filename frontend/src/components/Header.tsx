import "./Header.css"

export default function Header() {
    return (
        <nav className="navbar navbar-expand-lg custom-navbar">
            <div className="container">
                <div className="navbar-header">
                    <a className="navbar-brand website-name">Sudoku</a>
                </div>
                <div className="navbar-nav">
                    <a className="nav-link" data-toggle="modal" href="About" role="button">About</a>
                </div>
            </div>
        </nav>
    );
}