import React from 'react';
import API from '../service/api';

class Administracion extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      materias: [],
      nombreMateriaNueva: '',
      usuarioDocente: '',
      idMateriaDocente: '',
      errorMessageAgregarDocente: '',
      errorMessageCrearMateria: 'La materia no tiene nombre',
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
    if (this.state.idMateriaDocente === '') {
      this.setState({ errorMessageAgregarDocente: 'No hay materias' });
    } else {
      API.post(`/administracion/${this.state.idMateriaDocente}`, { usuario: this.state.usuarioDocente })
        .then(() => this.props.history.push('/administracion'))
        .catch(error => this.handleErrorUsuarioInexistente(error));
    }
  }

  inputInvalid(message) {
    if (message !== '') {
      return (
        <div className="invalid">
          <span className="oi oi-x" />
          {message}
        </div>
      );
    }
    return undefined;
  }

  handleErrorUsuarioInexistente(error) {
    this.setState({ errorMessageAgregarDocente: error.response.data });
  }

  handlerUsuarioDocente(e) {
    this.setState({ usuarioDocente: e.target.value });
  }

  handlerNombreMateria(e) {
    if (e.target.value !== '') {
      this.setState({ errorMessageCrearMateria: '' });
    } else {
      this.setState({ errorMessageCrearMateria: 'La materia no tiene nombre' });
    }
    this.setState({ nombreMateriaNueva: e.target.value });
  }

  handlerIdMateriaDocente(e) {
    this.setState({ idMateriaDocente: e.target.value });
  }

  crearMateriaNueva() {
    if (this.state.nombreMateriaNueva !== '') {
      API.post('/materia', { nombre: this.state.nombreMateriaNueva })
        .then(() => this.props.history.push('/administracion'))
        .catch(error => console.log(error.response));
    }
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
        <div>
          {this.inputInvalid(this.state.errorMessageCrearMateria)}
        </div>

        <form className="form-inline">
          <div className="form-group mx-sm-3 mb-2">
              Agregar Docente(Admin) a una materia:
          </div>
          <div className="input-group mx-sm-3 mb-2">
            <select className="form-control" value={this.state.value} onChange={e => this.handlerIdMateriaDocente(e)}>
              <option selected value="" disabled> No hay materias </option>
              {this.crearMateriasOptions()}
            </select>
          </div>
          <div>
            <input type="text" className="form-control" id="inputPassword3" placeholder="Usuario del Docente" onChange={e => this.handlerUsuarioDocente(e)} />
            <button type="submit" className="btn btn-primary mx-sm-3 mb-2" onClick={() => this.agregarUsuarioDocenteAMateria()}>Asignar</button>
          </div>
          <div>
            {this.inputInvalid(this.state.errorMessageAgregarDocente)}
          </div>
        </form>
      </div>
    );
  }
}
export default Administracion;
