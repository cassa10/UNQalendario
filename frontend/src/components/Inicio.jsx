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

  componentWillMount() {
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

  goToMateria(id) {
    this.props.history.push({
      pathname: '/materia/{id}',
      state: { idMateria: id },
    });
  }

  crearAlertaDeNoHayMateriasParaSuscribirse() {
    return (
      <div className="alert alert-primary col-12" role="alert">
        No hay materias en las que te puedas suscribir!
      </div>
    );
  }

  ponerNombreDocente(materia) {
    if (materia.nombreDocente.length === 0) {
      return (
        <p className="card-body">
          Sin profesor asignado, por ahora.
        </p>);
    }
    return (
      <p className="card-body">
        {materia.nombreDocente}
      </p>);
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
      <div className="col-12 col-md-4">
        <div className="card-materia">
          <div className="header">
            <h5 className="card-titulo ">
              {materia.nombre}
            </h5>
          </div>
          {this.ponerNombreDocente(materia)}¿
          <div>
            <p role="presentation" className="card-name-bottom" onClick={() => this.suscribirseAMateria(materia.id)}>
              SUSCRIBIRSE
            </p>
          </div>
        </div>
      </div>
    ));
  }

  crearVisualizacionMateriasSuscritas() {
    return this.state.materiasSuscritasDelUsuario.map(materia => (
      <div className="col-12 col-md-4">
        <div className="card-materia">
          <div className="header">
            <h5 className="card-titulo ">
              {materia.nombre}
            </h5>
          </div>
          {this.ponerNombreDocente(materia)}
          <div>
            <p role="presentation" className="card-name-bottom" onClick={() => this.goToMateria(materia.id)}>
              Ver
            </p>
          </div>
        </div>
      </div>
    ));
  }

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-12 titulo-banner">
            <h4 className="titulo-materias-divider">
              Tus Materias
            </h4>
          </div>
          {this.crearVisualizacionMateriasSuscritas()}
        </div>
        <div className="row">
          <div className="col-12 titulo-banner">
            <h4 className="titulo-materias-divider">
              Materias
            </h4>
          </div>
          {this.crearVisualizacionMaterias()}
        </div>
      </div>
    );
  }
}
export default Inicio;
