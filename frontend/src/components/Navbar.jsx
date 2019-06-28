import React from 'react';
import '../dist/css/Navbar.css';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownMenu from 'react-bootstrap/DropdownMenu';
import bellIcon from '../dist/icons/bell_icon.png';
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

  renderNotifications() {
    if (this.state.usuario !== '' && this.state.usuario.notificaciones.length) {
      return (this.state.usuario.notificaciones.map(
        noti => <Dropdown.Item key={`${noti.nombre}_${noti.fecha}`} className="itemNotificacion"> {noti.nombre},{noti.fecha} </Dropdown.Item>,
      ));
    }
    return (<Dropdown.Item className="itemNotificacion"> Uuuh!, no tenes notificaciones :( </Dropdown.Item>);
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
            <Dropdown drop="down" className="transparente">
              <Dropdown.Toggle id="dropdown-basic" className="transparente">
                <img src={bellIcon} alt="" className="bell" />
                <DropdownMenu>
                  {this.renderNotifications()}
                </DropdownMenu>
              </Dropdown.Toggle>
            </Dropdown>
            <ul className="navbar-nav mr-auto">
              <li className="nav-item active">
                <p className="nav-link" href="/inicio">INICIO</p>
              </li>
              <li className="nav-item">
                <p className="nav-link" href="/materias">MATERIAS</p>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}
export default Navbar;
