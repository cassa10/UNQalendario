
import React from 'react';
import { withRouter } from 'react-router';
import '../dist/css/InicioSession.css';
import API from '../service/api';

class CreacionCuenta extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showPass: 0,
      username: '',
      password: '',
      validInfo: '',
      imagesForSelect: [],
      imageselected: 'https://distrimar.s3.amazonaws.com/static/apm/img/misc/default_user.png',
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

  signUp() {
    if (this.state.username !== '' || this.state.password !== '') {
      API.post('/usuario', {
        nombre: this.state.username,
        password: this.state.password,
      })
        .then(() => this.props.history.push('/'))
        .catch(error => this.setState({ validInfo: error.response.data.message }));
    } else {
      this.setState({ validInfo: 'Datos Invalidos' });
    }
  }

  handlerPassword(event) {
    this.setState({ password: event.target.value });
  }

  handlerUsername(event) {
    this.setState({ username: event.target.value });
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
        <input className={classname} id="password" type="password" onChange={event => this.handlerPassword(event)} />
        <span className="focus-input100" data-placeholder="Contraseña" />
      </div>
    );
  }

  focuss() {
    document.getElementById('birthday').type = 'date';
  }

  blurr() {
    document.getElementById('birthday').type = 'text';
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

  pushToLogIn() {
    this.props.history.push('/');
  }

  selectedImage(image) {
    this.setState({ imageselected: image });
  }

  createImage(image) {
    return (
      <img src={image} className="image-SignUp img-thumbnail rounded-circle" alt="imgPerfil" data-dismiss="modal" role="presentation" onClick={() => this.selectedImage(image)} />
    );
  }

  createViewImagesForSelect() {
    return this.state.imagesForSelect.map(image => (
      <div className="col-4" key={image}>
        {this.createImage(image)}
      </div>
    ));
  }

  render() {
    return (
      <div className="logIn">
        <div className="container">
          <h1>
            UNQalendario
          </h1>
          <div className="row">
            <div className="col-12 col-sm-10 offset-sm-1 col-md-6 offset-md-3 col-lg-5 offset-lg-4 col-xl-4 offset-xl-4">
              <div className="logIn-containter">
                <div className="row justify-content-center logIn-container-img-title">
                  <div className="">
                    <div className="col-12">
                      <img src={this.state.imageselected} className="border rounded-circle border-dark" width="110" height="100" alt="Anonymous" />
                      <div className="pencilEdit selected" data-toggle="modal" data-target="#exampleModal">
                        <span className="oi oi-pencil selected" data-toggle="modal" data-target="#exampleModal" />
                      </div>
                    </div>
                    <div className="modal fade" id="exampleModal">
                      <div className="modal-dialog">
                        <div className="modal-content modal-content-signUp">
                          <div className="modal-header">
                            <h5 className="modal-title modal-title-signUp">Elige Una</h5>
                          </div>
                          <div className="modal-body">
                            <div className="row">
                              {this.createViewImagesForSelect()}
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="col-12">
                      <span className="signUp-title">
                        Registrate
                      </span>
                    </div>
                  </div>
                </div>
                {this.inputUsername()}
                {this.inputPassword()}
                {this.inputInvalidUser()}
                <div>
                  <button type="submit" className="btn btn-block btn-logIn" onClick={() => this.signUp()}> Crear Cuenta </button>
                </div>
                <div className="text-center p-t-115">
                  <span className="txt1">Ya tienes una cuenta? </span>
                  <a className="txt2" href="/">Inicia Sesion</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default withRouter(CreacionCuenta);
