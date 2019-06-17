Feature: Un usuario es notificado cuando el docente postea una tarea

  Scenario: Un Usuario es notificado cuando el docente postea una tarea
    Given Un UsuarioController y un UsuarioServicee
    And Un Usuario suscrito a una materia
    And Un docente es administrador de esa materia
    When un docente postea una tarea
    Then El Usuario recibe la notificacion


  Scenario: Un usuario que tiene una notificacion de tarea cuando el docete agrega 2 tareas mas ahora el usuario tiene 3 notificaciones
    Given Un UsuarioController y un UsuarioServicee
    And Un Usuario suscrito a una materia
    And Un docente es administrador de esa materia
    And Una tarea posteada en esa materia
    When un docente postea dos tareas
    Then El Usuario tiene tres notificaciones de tarea
