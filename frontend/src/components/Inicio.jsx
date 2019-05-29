import React from 'react';
import API from '../service/api';
import '../dist/css/Inicio.css';

class Inicio extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      materias: [],
      materiasFiltradas: [],
      materiasSuscritasDelUsuario: [],
      usuario: '',
    };
  }

  componentDidMount() {
    this.traerTodasLasMaterias();
    this.traerUsuario();
    this.traerMateriasSuscritasDelUsuario();
  }

  traerMateriasSuscritasDelUsuario() {
    API.get(`/materias/${this.props.location.state.username}`)
      .then(response => this.setState({ materiasSuscritasDelUsuario: response }));
  }

  traerUsuario() {
    API.get(`/usuario/${this.props.location.state.username}`)
      .then(response => this.setState({ usuario: response }));
  }

  traerTodasLasMaterias() {
    API.get('/materias')
      .then(response => this.setState({ materias: response, materiasFiltradas: response }));
  }

  handlerBuscarMateria(e) {
    const materiasFiltradasNuevas = this.state.materias
      .filter(m => m.nombre.includes(e.target.value));
    this.actualizarMateriasFiltradas(materiasFiltradasNuevas);
  }

  actualizarMateriasFiltradas(materiasFiltradasNuevas) {
    this.setState({ materiasFiltradas: materiasFiltradasNuevas });
  }

  suscribirseAMateria(idMateria) {
    API.post(`/suscribir/${idMateria}`, { idUsuario: this.props.location.state.username })
      .then(response => this.setState({ materiasSuscritasDelUsuario: response }));
  }

  crearAlertaDeNoHayMateriasParaSuscribirse() {
    return (
      <div className="alert alert-primary col-12" role="alert">
        No hay materias en las que te puedas suscribir!
      </div>
    );
  }

  crearVisualizacionMaterias() {
    const materiasNoSuscritas = this.state.materiasFiltradas
      .filter(m => this.state.materiasSuscritasDelUsuario
        .map(materia => materia.id).indexOf(m.id) === -1);
    if (materiasNoSuscritas.length === 0) {
      return (
        this.crearAlertaDeNoHayMateriasParaSuscribirse()
      );
    }
    return materiasNoSuscritas.map(materia => (
      <div className="col-3 pdb30" key={materia.id}>
        <div className="card text-center">
          <div className="card-header">
            {materia.nombre}
          </div>
          <div className="card-body">
            <button type="button" className="btn btn-primary" onClick={() => this.suscribirseAMateria(materia.id)}>
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
            <input className="form-control mr-sm-2" type="search" placeholder="Buscar Materia..." aria-label="Search" onChange={e => this.handlerBuscarMateria(e)} />
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
