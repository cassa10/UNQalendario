import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import React from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import Inicio from './Inicio';
import InicioSession from './InicioSession';
import CreacionCuenta from './CreacionCuenta';

export default class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route path="/inicio" render={props => <div><Inicio {...props} /> </div>} />
          <Route path="/crearCuenta" render={() => <CreacionCuenta />} />
          <Route path="/" render={props => <div><InicioSession {...props} /> </div>} />
        </Switch>
      </BrowserRouter>
    );
  }
}
