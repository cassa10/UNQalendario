import React from 'react';
import API from '../service/api';
import '../dist/css/Inicio.css';

class Inicio extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      materias: [],
      filtroMateria: '',
    };
  }

  componentDidMount() {
    API.get('/materias')
      .then(response => this.setState({ materias: response }));
  }

  handlerBuscarMateria(e) {
    this.setState({ filtroMateria: e.target.value });
  }

  eventBuscarMateria() {
    API.get(`/search/materia/${this.state.filtroMateria}`)
      .then(response => this.setState({ materias: response }));
  }

  crearVisualizacionMaterias() {
    return this.state.materias.map(materia => (
      <div className="col-3 pdb30" key={materia.id}>
        <div className="card text-center">
          <div className="card-header">
            {materia.nombre}
          </div>
          <div className="card-body">
            <button type="button" className="btn btn-primary">
              Suscribirse
            </button>
          </div>
          <div className="card-footer text-muted">
            Nombre del Profe
          </div>
        </div>
      </div>
    ));
  }

  render() {
    return (
      <div className="container">
        <h1>
          UNQalendario
        </h1>
        <div className="row pdb30">
          <div className="col-8">
            <input className="form-control mr-sm-2" type="search" placeholder="Materia..." aria-label="Search" onChange={e => this.handlerBuscarMateria(e)} />
          </div>
          <div className="col-4">
            <button className="btn btn-danger my-2 my-sm-0" type="submit" onClick={() => this.eventBuscarMateria()}>Buscar</button>
          </div>
        </div>
        <div className="row">
          {this.crearVisualizacionMaterias()}
        </div>
      </div>
    );
  }
}
export default Inicio;
