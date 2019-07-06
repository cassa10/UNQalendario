/* eslint-disable react/button-has-type */
import React from 'react';
import Swal from 'sweetalert2';
import API from '../service/api';
import '../dist/css/Materia.css';


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
      errorModalTituloTarea: '',
      errorModalFechaTarea: '',
    };
  }

  componentDidMount() {
    API.get(`/materia/${this.props.location.state.idMateria}`)
      .then(response => this.setState({ materia: response }))
      .catch(error => console.log(error));
  }

  setErrorCorrepondiente() {
    if (this.state.tarea.nombreTarea === '') {
      this.setState({ errorModalTituloTarea: 'Tarea sin nombre' });
    }
    if (this.state.tarea.fechaEntrega === '') {
      this.setState({ errorModalFechaTarea: 'Fecha invalida' });
    }
  }

  getDiaActual() {
    const hoy = new Date();
    const dia = this.parsePositiveIntTwoDigits(hoy.getDate());
    const mes = this.parsePositiveIntTwoDigits(hoy.getMonth() + 1);

    return `${hoy.getFullYear()}-${mes}-${dia}`;
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

  crearColorSegunFecha(daysLeft) {
    if (daysLeft >= 0) {
      if (daysLeft > 0) {
        return 'alert alert-info col-6';
      }
      return 'alert alert-warning col-6';
    }
    return 'alert alert-danger col-6';
  }

  crearBodyTarea(tarea) {
    const daysLeft = Math.round((new Date(tarea.fecha)
      .getTime() - new Date().getTime()) / 86400000) + 1;

    return (
      <div className={this.crearColorSegunFecha(daysLeft)} role="alert">
        <div className="row">
          <div className="col-3">
            {this.formato(tarea.fecha)}
          </div>
          <div className="col-3">
            {tarea.nombre}
          </div>
          <div className="col-5">
            {this.crearTextoDeDiasRestantes(daysLeft)}
          </div>
          <div className="col-1">
            {this.crearBotonEliminar(tarea)}
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

  eliminarTarea(tarea) {
    return (
      Swal.fire({
        title: '¿Estas seguro?',
        text: `Si la eliminas, no podras recuperar a la tarea ${tarea.nombre} `,
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#BB002D',
        cancelButtonColor: '#007E60',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.value) {
          Swal.fire(
            'Listo!',
            `${tarea.nombre} ha sido eliminada`,
            'success',
          );
          console.log(tarea);
          API.post(`/eliminarTarea/${this.props.location.state.idMateria}`, {
            usuario: `${this.props.location.state.idUsuario}`,
            nombreTarea: `${tarea.nombre}`,
            fechaEntrega: `${this.formato(tarea.fecha)}`,
          }).then(response => this.setState({ materia: response }))
            .catch(error => console.log(error.response));
        }
      })
    );
  }

  crearBotonEliminar(tarea) {
    if (this.esUsuarioAdmin()) {
      return (
        <span role="presentation" className="oi oi-trash" onClick={() => this.eliminarTarea(tarea)} />
      );
    }
    return null;
  }

  esUsuarioAdmin() {
    return (this.state.materia.administradores
      .map(a => a.id).includes(this.props.location.state.idUsuario));
  }

  crearBotonAdministracion() {
    if (this.esUsuarioAdmin()) {
      return (
        <button type="button" className="btn btn-success mb-4" data-toggle="modal" data-target="#exampleModal">
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
    this.setState({ errorModalFechaTarea: '', tarea: { ...oldTarea, fechaEntrega: this.formato(event.target.value) } });
  }

  handlerTituloTarea(event) {
    const oldTarea = this.state.tarea;
    this.setState({ errorModalTituloTarea: '', tarea: { ...oldTarea, nombreTarea: event.target.value } });
  }

  swalYAPIPost() {
    return (Swal.fire({
      title: '¿Estas seguro?',
      text: `¿Quieres confirmar la tarea ${this.state.tarea.nombreTarea} para la fecha: ${this.state.tarea.fechaEntrega}?`,
      type: 'question',
      showCancelButton: true,
      confirmButtonColor: '#58D68D',
      cancelButtonColor: '#F97E56',
      confirmButtonText: 'Confirmar!',
      cancelButtonText: 'Volver atras',
    }).then((result) => {
      if (result.value) {
        API.post(`/tarea/${this.state.materia.id}`, {
          fechaEntrega: this.state.tarea.fechaEntrega,
          nombreTarea: this.state.tarea.nombreTarea,
          usuario: this.props.location.state.idUsuario,
        })
          .then(response => this.setState({ materia: response }), this.cancelarTarea())
          .catch(error => console.log(error.response));
        Swal.fire(
          'Completado!',
          'La tarea fue publicada',
          'success',
        );
      }
    })
    );
  }

  crearTareaNueva() {
    if (this.state.tarea.nombreTarea === '' || this.state.tarea.fechaEntrega === '') {
      this.setErrorCorrepondiente();
    } else {
      console.log({
        fechaEntrega: this.state.tarea.fechaEntrega,
        nombreTarea: this.state.tarea.nombreTarea,
        usuario: this.props.location.state.idUsuario,
      });
      this.swalYAPIPost();
    }
  }

  cancelarTarea() {
    this.setState({ errorModalFechaTarea: '', errorModalTituloTarea: '', tarea: { nombreTarea: '', fechaEntrega: '' } });
  }

  formato(texto) {
    return texto.replace(/^(\d{4})-(\d{2})-(\d{2})$/g, '$3/$2/$1');
  }

  handleSiExisteErrorEnTitulo() {
    if (this.state.errorModalTituloTarea !== '') {
      return (
        <div className="invalid">
          <span className="oi oi-x" />
          {this.state.errorModalTituloTarea}
        </div>
      );
    }
    return undefined;
  }

  handleSiExisteErrorEnFecha() {
    if (this.state.errorModalFechaTarea !== '') {
      return (
        <div className="invalid">
          <span className="oi oi-x" />
          {this.state.errorModalFechaTarea}
        </div>
      );
    }
    return undefined;
  }

  parsePositiveIntTwoDigits(n) {
    if (n < 10) {
      return `0${n}`;
    }
    return n;
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
              <form>
                <div className="modal-body">
                  <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Titulo</label>
                    <input type="text" className="form-control" placeholder="..." value={this.state.tarea.nombreTarea} onChange={event => this.handlerTituloTarea(event)} />
                  </div>
                  {this.handleSiExisteErrorEnTitulo()}
                  <div className="form-group">
                    <label htmlFor="fecha1">Fecha</label>
                    <input type="date" className="form-control" id="fecha1" min={this.getDiaActual()} pattern="[0-9]{4}/[0-9]{2}/[0-9]{2}" onChange={event => this.handlerFecha(event)} />
                  </div>
                  {this.handleSiExisteErrorEnFecha()}
                </div>
                <div className="modal-footer">
                  <button type="reset" className="btn btn-secondary" data-dismiss="modal" onClick={() => this.cancelarTarea()}>Cancelar</button>
                  <button type="reset" className="btn btn-primary" onClick={() => this.crearTareaNueva()}>Crear</button>
                </div>
              </form>
            </div>
          </div>
        </div>
        {this.crearVisorTareas()}
      </div>
    );
  }
}
export default Materia;
