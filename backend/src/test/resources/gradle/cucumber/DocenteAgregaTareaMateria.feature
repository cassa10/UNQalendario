Feature: Agregar tarea a materia

  Scenario: Un Docente administrador de una Materia le agrega una Tarea
    Given Un setUp de UNQalendario
    And El docente "Jano" administrador de la Materia "Mate2"
    When Este Docente agrega una Tarea en la Materia en la fecha <2019-07-03> con el nombre "Tp mate"
    Then La Materia posee una Tarea "Tp mate" la Materia "Mate2" en la fecha <2019-07-03>
