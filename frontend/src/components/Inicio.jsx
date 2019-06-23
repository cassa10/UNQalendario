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
      .then(response => this.setState({ materiasSuscritasDelUsuario: response }))
      .catch(error => console.log(error));
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
      .then(response => this.setState({ materiasSuscritasDelUsuario: response }))
      .catch(error => console.log(error.response.data));
  }

  goToMateria(id) {
    this.props.history.push({
      pathname: '/materia/{id}',
      state: { idMateria: id, idUsuario: this.props.location.state.username },
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
    if (materia.administradores.length === 0) {
      return (
        <p className="card-body">
          Sin profesor asignado, por ahora.
        </p>);
    }
    return (
      <p className="card-body">
        { materia.administradores[0].nombrePersona.concat(', ', materia.administradores[0].apellido) }
      </p>);
  }

  agregarVerMateriaSiSoyAdmin(materia) {
    const adminFilter = materia.administradores
      .map(u => u.nombreUsuario).filter(u => u === this.state.usuario.nombreUsuario);
    if (adminFilter.length === 1) {
      return (
        <p role="presentation" className="card-name-bottom" onClick={() => this.goToMateria(materia.id)}>
          Ver
        </p>
      );
    }
    return (
      undefined
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
      <div className="col-12 col-md-4" key={materia.id}>
        <div className="card-materia">
          <div className="header">
            <h5 className="card-titulo ">
              {materia.nombre}
            </h5>
          </div>
          {this.ponerNombreDocente(materia)}
          <div>
            {this.agregarVerMateriaSiSoyAdmin(materia)}
            <p role="presentation" className="card-name-bottom" onClick={() => this.suscribirseAMateria(materia.id)}>
              SUSCRIBIRSE
            </p>
          </div>
        </div>
      </div>
    ));
  }

  crearTextoDeDiasRestantes(daysLeft) {
    if (daysLeft >= 0) {
      if (daysLeft > 0) {
        return `${daysLeft} día(s) restantes`;
      }
      return 'Hoy se entrega';
    }
    return 'Tarea finalizada';
  }

  handleMensajeDeNoti(noti) {
    const daysLeft = Math.round((new Date(noti.fecha)
      .getTime() - new Date().getTime()) / 86400000) + 1;
    return (
      <div className="textoDiasRestantes">
        {this.crearTextoDeDiasRestantes(daysLeft)}
      </div>
    );
  }

  crearVisualizacionMateriasSuscritas() {
    return this.state.materiasSuscritasDelUsuario.map(materia => (
      <div className="col-12 col-md-4" key={materia.id}>
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
