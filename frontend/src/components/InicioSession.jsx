import React from 'react';
import { withRouter } from 'react-router';
import API from '../service/api';
import '../dist/css/InicioSession.css';

class InicioSession extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showPass: 0,
      username: '',
      password: '',
      validInfo: '',
    };
  }

  componentDidMount() {
    document.getElementById('father').className = 'logIn';
  }

  handleShowPassword() {
    if (this.state.showPass === 0) {
      document.getElementById('password').type = 'text';
      document.getElementById('icon-password').className = 'btn-show-pass btn-show-pass-selected';
      this.setState({ showPass: 1 });
    } else {
      document.getElementById('password').type = 'password';
      this.setState({ showPass: 0 });
      document.getElementById('icon-password').className = 'btn-show-pass';
    }
  }

  logIn() {
    API.post('/verificar', { usuario: this.state.username, password: this.state.password })
      .then(response => this.props.history.push({
        pathname: '/inicio',
        state: { username: response },
      }))
      .catch(error => this.setState({ validInfo: error.response.data }));
  }

  handlerPassword(event) {
    this.setState({ password: event.target.value });
  }

  handlerUsername(event) {
    this.setState({ username: event.target.value });
  }

  inputInvalidUser() {
    if (this.state.validInfo !== '') {
      return (
        <div className="invalid">
          <span className="oi oi-x" />
          {this.state.validInfo}
        </div>
      );
    }
    return undefined;
  }

  inputUsername() {
    let classname = 'input100';
    if (this.state.username !== '') {
      classname = 'input100 has-val';
    }
    return (
      <div className="wrap-input100">
        <input className={classname} type="text" id="username" onChange={event => this.handlerUsername(event)} />
        <span className="focus-input100" data-placeholder="Usuario" />
      </div>
    );
  }

  inputPassword() {
    let classname = 'input100';
    if (this.state.password !== '') {
      classname = 'input100 has-val';
    }
    return (
      <div className="wrap-input100">
        <span className="btn-show-pass" id="icon-password" role="presentation" onClick={() => this.handleShowPassword()}>
          <i className="oi oi-eye" />
        </span>
        <input className={classname} id="password" type="password" onChange={event => this.handlerPassword(event)} onKeyUp={event => this.handlerEnterKey(event)} />
        <span className="focus-input100" data-placeholder="Contraseña" />
      </div>
    );
  }

  handlerEnterKey(event) {
    if (event.keyCode === 13) {
      this.logIn();
    }
  }

  pushToLogIn() {
    this.props.history.push('/');
  }

  render() {
    return (
      <div className="logIn">
        <div className="container">
          <div className="row">
            <h1 className="col-12">
              UNQalendario
            </h1>
            <div className="col-12 col-sm-10 offset-sm-1 col-md-6 offset-md-3 col-lg-5 offset-lg-4 col-xl-4 offset-xl-4">
              <div className="logIn-containter">
                <div className="row justify-content-center logIn-container-img-title">
                  <div className="d-flex justify-content-center col-12">
                    <img src="https://distrimar.s3.amazonaws.com/static/apm/img/misc/default_user.png" className="border rounded-circle border-dark float-center" width="110" height="100" alt="Anonymous" />
                  </div>
                  <span className="logIn-title logIn-container-img-title">
                        Bienvenido
                  </span>
                </div>
                {this.inputUsername()}
                {this.inputPassword()}
                {this.inputInvalidUser()}
                <div>
                  <button type="submit" className="btn btn-block btn-logIn" onClick={() => this.logIn()}> Iniciar Sesion </button>
                </div>
                <div className="text-center p-t-115">
                  <span className="txt1">No tiene una cuenta? </span>
                  <a className="txt2" href="/crearCuenta">Registrate</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default withRouter(InicioSession);
