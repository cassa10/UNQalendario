Feature: Ver tareas

  Scenario: Cuando agrego una tarea a la materia la materia tiene una tarea
    Given Una materia con un profesor
    When Agrego una tarea a la materia
    And Le pregunto a la materia por las tareas
    Then La materia tiene una tarea

  Scenario: Como estudiante quiero ver las tareas de una materia
    Given Una materia con tareas
    And Un usuario suscrito a esa materia
    When El usuario quiere ver las tareas
    Then Le aparece una lista de tareas para esa materia