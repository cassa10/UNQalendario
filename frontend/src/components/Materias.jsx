import React from 'react';
import API from '../service/api';

class Materias extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        usuario: [],
        materias: [],
        materiasFiltradas: [],
    };
  }

  componentDidMount() {
    this.traerUsuario();
    this.traerTodasLasMaterias();
  }

  traerUsuario() {
    API.get(`/usuario/${this.props.location.state.username}`)
      .then(response => this.setState({ usuario: response }));
  }

  traerTodasLasMaterias() {
    API.get('/materias')
      .then(response => this.setState({ materias: response, materiasFiltradas: response }));
  }

  crearMaterias() {
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
          <p className="card-body">
            Nombre del profesor
          </p>
          <div>
            <p role="presentation" className="card-name-bottom" onClick={() => this.suscribirseAMateria(materia.id)}>
              SUSCRIBIRSE
            </p>
          </div>
        </div>
      </div>
    ));
  }

  render() {
    return (
      <div className="container">
        {this.crearMaterias()}
      </div>
    );
  }
}
export default Materias;
