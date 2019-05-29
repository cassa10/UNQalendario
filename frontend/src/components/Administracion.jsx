import React from 'react';
import API from '../service/api';

class Administracion extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      nombreMateriaNueva: '',
    };
  }

  componentDidMount() {
  }

  handlerNombreMateria(e) {
    this.setState({ nombreMateriaNueva: e.target.value });
  }

  crearMateriaNueva() {
    API.post('/materia', { nombre: this.state.nombreMateriaNueva })
      .catch(error => console.log(error.response));
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
      </div>
    );
  }
}
export default Administracion;
