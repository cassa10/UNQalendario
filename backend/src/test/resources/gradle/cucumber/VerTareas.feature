Feature: Ver tareas

  Scenario: Como estudiante quiero ver las tareas de una materia
    Given: Una materia con tareas
    And: Un usuario suscrito a esa materia
    When: El usuario quiere ver las tareas
    Then: Le aparece una lista de pares de tareas y fechas para esa materia