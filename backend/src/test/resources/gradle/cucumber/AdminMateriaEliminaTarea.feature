Feature: Administrador de una materia elimina una tarea a esa materia

    Scenario: Admin de una materia elimina una tarea a esa materia y retorna un response ok con la tarea fuera de la materia en el body
        Given Un setup una materiaController y usuarioControllerr
        And Un usuario y una materiaa
        And Ese usuario es administrador de esa materiaa
        And Una tareaa "Parcial" con fecha "18/11/2029"
        And MateriaController se le pide agregar esta tarea en esa Materiaa
        When MateriaController se le pide eliminar esta tarea en esa Materia
        Then Se retorna un response ok con esta Materia que no posee ninguna materia en el body


    Scenario: Admin de una materia con tareas elimina una tarea a esa materia y retorna un response ok con la lista de tareas nueva dentro de esa materia en el body
        Given Un setup una materiaController y usuarioControllerr
        And Un usuario y una materiaa
        And Una lista de tareas en esa materiaa
        And Ese usuario es administrador de esa materiaa
        And Una tareaa "EntregaTP" con fecha "12/11/2028"
        And MateriaController se le pide eliminar esta tarea en esa Materia
        Then Se retorna un response ok con esta Materia que contiene todas las tareas anteriores sin la ultima tarea en el body


    Scenario: Uno de los Admins de una materia agrega una tarea a esa materia, otro la elimina y retorna un response ok sin la tarea dentro de la materia en el body
        Given Un setup una materiaController y usuarioControllerr
        And Tres usuarios y una materiaa
        And Estos usuarios son administradores de esa materiaa
        And Una tareaa "Parcial" con fecha "18/11/2029"
        When El segundo Usuario pide a la MateriaController eliminar esta tarea en esa Materia
        Then Se retorna un response ok con esta Materia que no posee ninguna materia en el body