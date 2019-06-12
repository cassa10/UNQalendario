import React from 'react';
import API from '../service/api';

class Materia extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      materia: {
        nombre: '',
        tareas: [],
        administradores: [],
      },
      tarea: {
        fechaEntrega: '',
        nombreTarea: '',
      },
    };
  }

  componentDidMount() {
    API.get(`/materia/${this.props.location.state.idMateria}`)
      .then(response => this.setState({ materia: response }))
      .catch(error => console.log(error));
  }

  crearBodyTarea(tarea) {
    return (
      <div className="alert alert-info col-6" role="alert">
        <div className="row">
          <div className="col-3">
            {this.formato(tarea.fecha)}
          </div>
          <div className="col-3">
            {tarea.nombre}
          </div>
          <div className="col-4">
            {Math
              .round((new Date(tarea.fecha)
                .getTime() - new Date().getTime()) / 86400000) + 1} dias restantes
          </div>
        </div>
      </div>
    );
  }

  crearVisorTareas() {
    return this.state.materia.tareas.map((tarea, i) => (
      <div key={i}>
        {this.crearBodyTarea(tarea)}
      </div>
    ));
  }

  crearBotonAdministracion() {
    if (this.state.materia.administradores
      .map(a => a.id).includes(this.props.location.state.idUsuario)) {
      return (
        <button type="button" className="btn btn-danger mb-4" data-toggle="modal" data-target="#exampleModal">
          Nueva Tarea
        </button>
      );
    }
    return null;
  }

  focuss() {
    document.getElementById('birthday').type = 'date';
  }

  blurr() {
    document.getElementById('birthday').type = 'text';
  }

  handlerFecha(event) {
    const oldTarea = this.state.tarea;
    this.setState({ tarea: { ...oldTarea, fechaEntrega: this.formato(event.target.value) } });
  }

  handlerTituloTarea(event) {
    const oldTarea = this.state.tarea;
    this.setState({ tarea: { ...oldTarea, nombreTarea: event.target.value } });
  }

  crearTareaNueva() {
    API.post(`/tarea/${this.state.materia.id}`, this.state.tarea)
      .then(response => this.setState({ materia: response }), this.cancelarTarea())
      .catch(error => console.log(error.response));
  }

  cancelarTarea() {
    this.setState({ tarea: { nombreTarea: '', fechaEntrega: '' } });
  }

  formato(texto) {
    return texto.replace(/^(\d{4})-(\d{2})-(\d{2})$/g, '$3/$2/$1');
  }

  render() {
    return (
      <div className="container">
        <div className="row">
          <h1 className="col-3">
            {this.state.materia.nombre}
          </h1>
        </div>
        {this.crearBotonAdministracion()}
        <div className="modal fade" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLabel">Nueva Tarea</h5>
              </div>
              <div className="modal-body">
                <form>
                  <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Titulo</label>
                    <input type="text" className="form-control" placeholder="..." value={this.state.tarea.nombreTarea} onChange={event => this.handlerTituloTarea(event)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="fecha1">Fecha</label>
                    <input type="date" className="form-control" id="fecha1" min="2019-01-01" pattern="[0-9]{4}/[0-9]{2}/[0-9]{2}" onChange={event => this.handlerFecha(event)} />
                  </div>
                </form>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={() => this.cancelarTarea()}>Cancelar</button>
                <button type="button" className="btn btn-primary" onClick={() => this.crearTareaNueva()}>Crear</button>
              </div>
            </div>
          </div>
        </div>
        {this.crearVisorTareas()}
      </div>
    );
  }
}
export default Materia;
