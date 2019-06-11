import React from 'react';
import API from '../service/api';

class Materia extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      materia: {
        nombre: '',
        tareas: [],
      },
    };
  }

  componentDidMount() {
    API.get(`/materia/${this.props.location.state.idMateria}`)
      .then(response => this.setState({ materia: response}))
      .catch(error => console.log(error));
  }

  crearVisorTareas() {
    return this.state.materia.tareas.map(tarea => (
      <div>
        {tarea.fecha}
        {tarea.nombre}
      </div>
    ));
  }

  render() {
    return (
      <div className="container">
        <h1>
          {this.state.materia.nombre}
        </h1>
        {this.crearVisorTareas()}
      </div>
    );
  }
}
export default Materia;
