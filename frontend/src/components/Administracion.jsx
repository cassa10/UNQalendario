import React from 'react';
import API from '../service/api';

class Administracion extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      materias: [],
      nombreMateriaNueva: '',
      usuarioDocente: '',
      idMateriaDelUsuario: '',
    };
  }

  componentDidMount() {
    this.traerTodasLasMaterias();
  }

  traerTodasLasMaterias() {
    API.get('/materias')
      .then(response => this.setState({ materias: response }));
  }

  agregarUsuarioDocenteAMateria() {
    API.post(`/administracion/${this.state.idMateriaDelUsuario}`, { usuario: this.state.usuarioDocente })
      .then(() => this.props.history.push('/administracion'))
      .catch(response => this.handleErrorUsuarioInexistente(response));
  }

  handlerUsuarioDocente(e) {
    this.setState({ usuarioDocente: e.target.value });
  }

  handlerNombreMateria(e) {
    this.setState({ nombreMateriaNueva: e.target.value });
  }

  handlerIdMateriaDocente(e) {
    this.setState({ idMateriaDelUsuario: e.target.value.nombre });
  }

  crearMateriaNueva() {
    API.post('/materia', { nombre: this.state.nombreMateriaNueva })
      .then(() => this.props.history.push('/administracion'))
      .catch(error => console.log(error.response));
  }

  crearMateriasOptions() {
    return this.state.materias.map(
      mat => <option value={mat.id}>{mat.nombre}</option>,
    );
  }

  render() {
    return (
      <div className="container">
        <h1>
            Administracion de UNQalendario
        </h1>
        <form className="form-inline">
          <div className="form-group mb-2">
              Crear una Materia:
          </div>
          <div className="form-group mx-sm-3 mb-2">
            <input type="text" className="form-control" id="inputPassword2" placeholder="Nombre" onChange={e => this.handlerNombreMateria(e)} />
          </div>
          <button type="submit" className="btn btn-primary mb-2" onClick={() => this.crearMateriaNueva()}>Crear</button>
        </form>

        <form className="form-inline">
          <div className="form-group mb-2">
              Agregar Docente(Admin) a una materia:
          </div>
          <div className="input-group mb-2">
            <select className="form-control" value={this.state.value} onChange={e => this.handlerIdMateriaDocente(e)} placeholder="Materias">
              <option selected value="" disabled> Materias </option>
              {this.crearMateriasOptions()}
            </select>
          </div>
          <div>
            <input type="text" className="form-control" id="inputPassword2" placeholder="Usuario del Docente" onChange={e => this.handlerUsuarioDocente(e)} />
            <button type="submit" className="btn btn-primary mb-2" onClick={() => this.asignarDocente()}>Asignar</button>
          </div>
        </form>
        
      </div>
    );
  }
}
export default Administracion;
