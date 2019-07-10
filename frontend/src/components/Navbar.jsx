import React from 'react';
import '../dist/css/Navbar.css';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownMenu from 'react-bootstrap/DropdownMenu';
import bellIcon from '../dist/icons/bell_icon.png';
import userIcon from '../dist/icons/user_icon.png';
import API from '../service/api';

class Navbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      usuario: {
        id: '',
        nombreUsuario: '',
        nombrePersona: '',
        apellido: '',
        notificaciones: [],
        nuevasNotificaciones: '',
      },
    };
  }

  componentDidMount() {
    this.traerUsuario();
  }

  traerUsuario() {
    API.get(`/usuario/${this.props.location.state.username}`)
      .then(response => this.setState({ usuario: response }));
  }

  goToInicio() {
    this.props.history.push({
      pathname: '/inicio',
      state: { username: this.state.usuario.id },
    });
  }

  headerNavbar() {
    return (
      <div className="header-navbar">
        <p role="presentation" className="navbar-brand titulo-navbar" onClick={() => this.goToInicio()}>UNQalendario</p>
      </div>
    );
  }

  nameCapitalized(name) {
    return (name.charAt(0).toUpperCase() + name.slice(1));
  }

  mostrarNombreYApellidoSiTiene(usuario) {
    if (usuario.apellido !== '' || usuario.nombrePersona !== '') {
      return (
        <span className="userNombreYApellidoNav">
          <span className="pipeDelNombreUsuario"> | </span>
          <span className="font-weight-normal navbarUsuarioNombreYApellido">
            {`${this.nameCapitalized(this.state.usuario.nombrePersona)} ${this.nameCapitalized(this.state.usuario.apellido)}`}
          </span>
        </span>
      );
    }
    return (undefined);
  }

  pushMateria(id) {
    this.props.history.push({
      pathname: '/materia/{id}',
      state: {
        idMateria: id,
        username: this.props.location.state.username,
        idUsuario: this.props.location.state.username,
      },
    });
  }

  formato(texto) {
    return texto.replace(/^(\d{4})-(\d{2})-(\d{2})$/g, '$3/$2/$1');
  }

  borrarNotificacionesNuevas() {
    const oldUsuario = this.state.usuario;
    this.setState({ usuario: { ...oldUsuario, nuevasNotificaciones: 0 } });
    API.post(`/borrarNotifiacionesVistas/${this.state.usuario.id}`);
  }

  borrarNotificacion(id) {
    API.post(`/borrar/notificaciones/${this.state.usuario.id}`, { idTarea: id })
      .then(response => this.setState({ usuario: response }));
  }

  renderNotifications() {
    if (this.state.usuario !== '' && this.state.usuario.notificaciones.length) {
      return (this.state.usuario.notificaciones.map(
        noti => (
          <Dropdown.Item key={`${noti.idMateria}_${noti.tarea.id}`} className="itemNotificacion">
            <div className="row">
              <h6 className="col-10" onClick={() => this.pushMateria(noti.idMateria)} role="presentation">{noti.titulo}</h6>
              <span className="oi oi-circle-x col-2" role="presentation" onClick={() => this.borrarNotificacion(noti.tarea.id)} />
              <p className="col-12" onClick={() => this.pushMateria(noti.idMateria)} role="presentation"> {noti.tarea.nombre} (Para el dia: {this.formato(noti.tarea.fecha)})</p>
            </div>
          </Dropdown.Item>
        ),
      ));
    }
    return (<Dropdown.Item className="itemNotificacion"> Uuuh!, no tenes notificaciones :( </Dropdown.Item>);
  }

  renderNombreUsuario() {
    return (
      <Dropdown.Header className="header-user-icon">
        <span className="font-italic navbarNombreUsuario">
          {`@${this.state.usuario.nombreUsuario} `}
        </span>
        {this.mostrarNombreYApellidoSiTiene(this.state.usuario)}
      </Dropdown.Header>
    );
  }

  crearCantidadNotificacionesNuevas() {
    if (this.state.usuario.nuevasNotificaciones != 0) {
      return (
        <p className="form-inline my-2 my-lg-0 p-2 d-flex justify-content-center numeroNoti">{this.state.usuario.nuevasNotificaciones}</p>
      );
    }
    return undefined;
  }

  render() {
    return (
      <nav className="navbar navbar-expand-lg background-navbar">
        <div className="container">
          {this.headerNavbar()}
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon" />
          </button>
          {console.log(this.state.usuario)}
          <div className="collapse navbar-collapse d-flex-lg bd-highlight" id="navbarSupportedContent">
            <form className="form-inline my-2 my-lg-0 p-2 flex-grow-1 bd-highlight d-flex justify-content-center">
              <input className="inputSearch" type="text" placeholder="Buscar Materia" aria-label="Search" />
              <img role="presentation" alt="lupa" src="https://image.flaticon.com/icons/svg/70/70376.svg" height="16" />
            </form>
            {this.crearCantidadNotificacionesNuevas()}
            <Dropdown drop="down" className="transparente" onClick={() => this.borrarNotificacionesNuevas()}>
              <Dropdown.Toggle id="dropdown-basic" className="transparente">
                <img src={bellIcon} alt="" className="bell" />
                <DropdownMenu alignRight>
                  {this.renderNotifications()}
                </DropdownMenu>
              </Dropdown.Toggle>
            </Dropdown>
            <Dropdown drop="down" className="transparente user-icon">
              <Dropdown.Toggle id="dropdown-basic" className="transparente">
                <img src={userIcon} alt="" className="bell" />
                <DropdownMenu alignRight>
                  <Dropdown.Header>{this.renderNombreUsuario()}</Dropdown.Header>
                  <Dropdown.Item className="logout" onClick={() => this.props.history.push('/')}>Cerrar sesión</Dropdown.Item>
                </DropdownMenu>
              </Dropdown.Toggle>
            </Dropdown>
          </div>
        </div>
      </nav>
    );
  }
}
export default Navbar;
