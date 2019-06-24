Feature: Administrador de una materia agrega una tarea a esa materia

    Scenario: Admin de una materia agrega una tarea a esa materia y retorna un response ok con la tarea dentro de la materia en el body
        Given Un setup una materiaController y usuarioController
        And Un usuario y una materia
        And Ese usuario es administrador de esa materia
        And Una tarea "Parcial" con fecha "18/11/2029"
        When MateriaController se le pide agregar esta tarea en esa Materia
        Then Se retorna un response ok con esta Materia que posee solamente esta tarea en el body

    Scenario: Admin de una materia agrega una tarea donde ya existe una identica en esa materia y retorna un response bad request la tarea ya existe en el body
        Given Un setup una materiaController y usuarioController
        And Un usuario y una materia
        And Ese usuario es administrador de esa materia
        And Una tarea "Parcial" con fecha "18/11/2029"
        And Existe una tarea identica a la anterior
        When MateriaController se le pide agregar esta tarea en esa Materia
        Then Se retorna un response Bad Request "La tarea ya existe" en el body y la materia sigue intacta

    Scenario: Un Usuario intenta agregar una tarea a una materia y al no ser admin retorna un response not found con Usuario Invalido en el body
        Given Un setup una materiaController y usuarioController
        And Un usuario y una materia
        And Una tarea "Parcial" con fecha "18/11/2029"
        When MateriaController se le pide agregar esta tarea en esa Materia
        Then Se retorna un response not found con "Usuario Invalido" en el body y la materia no contiene la tarea anterior

    Scenario: Un Usuario intenta agregar una tarea a una materia que no existe y retorna un response not found con Path Invalido en el body
        Given Un setup una materiaController y usuarioController
        And Un usuario nuevo
        And Una tarea "Parcial" con fecha "18/11/2029"
        When MateriaController se le pide agregar esta tarea en una Materia que no existe
        Then Se retorna un response not found con "Path Invalido" en el body

    Scenario: Admin de una materia con tareas agrega una tarea mas a esa materia y retorna un response ok con la lista de tareas nueva dentro de esa materia en el body
        Given Un setup una materiaController y usuarioController
        And Un usuario y una materia
        And Una lista de tareas en esa materia
        And Ese usuario es administrador de esa materia
        And Una tarea "EntregaTP" con fecha "12/11/2028"
        When MateriaController se le pide agregar esta tarea en esa Materia
        Then Se retorna un response ok con esta Materia que contiene todas las tareas anteriores y esta tarea en el body

    Scenario: Uno de los Admins de una materia agrega una tarea a esa materia y retorna un response ok con la tarea dentro de la materia en el body
        Given Un setup una materiaController y usuarioController
        And Tres usuarios y una materia
        And Estos usuarios son administradores de esa materia
        And Una tarea "Parcial" con fecha "18/11/2029"
        When El segundo Usuario pide a la MateriaController agregar esta tarea en esa Materia
        Then Se retorna un response ok con esta Materia que posee solamente esta tarea en el body