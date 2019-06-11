import React from 'react';
import API from '../service/api';
import '../dist/css/Navbar.css';

class Navbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  componentDidMount() {
  }

  render() {
    return (
      <nav className="container-fluid navbar navbar-expand-lg background-navbar">
        <p role="presentation" className="navbar-brand titulo-navbar" onClick={() => this.goToInicio()}>UNQalendario</p>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon" />
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <form className="form-inline my-2 my-lg-0">
            <input className="inputSearch" type="text" placeholder="Buscar Materia" aria-label="Search" />
            <img role="presentation" alt="lupa" src="https://image.flaticon.com/icons/svg/70/70376.svg" height="16" />
          </form>
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
              <a className="nav-link" href="/inicio">Inicio <span className="sr-only">(current)</span></a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/materias">Materias</a>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}
export default Navbar;
