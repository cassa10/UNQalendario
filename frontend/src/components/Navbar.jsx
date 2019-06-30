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
      usuario: '',
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

  renderNotifications() {
    if (this.state.usuario !== '' && this.state.usuario.notificaciones.length) {
      return (this.state.usuario.notificaciones.map(
        noti => <Dropdown.Item key={`${noti.nombre}_${noti.fecha}`} className="itemNotificacion"> {noti.nombre},{noti.fecha} </Dropdown.Item>,
      ));
    }
    return (<Dropdown.Item className="itemNotificacion"> Uuuh!, no tenes notificaciones :( </Dropdown.Item>);
  }


  renderNombreUsuario() {
    if (this.state.usuario !== '') {
      return (<Dropdown.Header className="header-user-icon">{this.nameCapitalized(this.state.usuario.nombrePersona)}</Dropdown.Header>
      );
    }
    return (null);
  }

  render() {
    return (
      <nav className="navbar navbar-expand-lg background-navbar">
        <div className="container">
          {this.headerNavbar()}
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon" />
          </button>

          <div className="collapse navbar-collapse d-flex-lg bd-highlight" id="navbarSupportedContent">
            <form className="form-inline my-2 my-lg-0 p-2 flex-grow-1 bd-highlight d-flex justify-content-center">
              <input className="inputSearch" type="text" placeholder="Buscar Materia" aria-label="Search" />
              <img role="presentation" alt="lupa" src="https://image.flaticon.com/icons/svg/70/70376.svg" height="16" />
            </form>
            <ul className="navbar-nav mr-auto">
              <li className="nav-item active">
                <p className="nav-link" href="/inicio">INICIO</p>
              </li>
              <li className="nav-item">
                <p className="nav-link" href="/materias">MATERIAS</p>
              </li>
            </ul>
            <Dropdown drop="down" className="transparente">
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
