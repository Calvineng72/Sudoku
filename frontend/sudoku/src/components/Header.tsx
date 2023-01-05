import "./Header.css"

export default function Header() {
    return (
        <nav className="navbar navbar-expand-lg custom-navbar" style={{ backgroundColor: "#d7f8da" }}>
            <div className="container">
                <div className="navbar-header">
                    <a className="navbar-brand" href="#">Sudoku</a>
                </div>
                <div className="navbar-nav">
                    <a className="nav-link active" href="#">Home</a>
                    <a className="nav-link" href="#">Small</a>
                    <a className="nav-link" href="#">Normal</a>
                    <a className="nav-link" href="#">Large</a>
                </div>
            </div>
        </nav>
    );
}