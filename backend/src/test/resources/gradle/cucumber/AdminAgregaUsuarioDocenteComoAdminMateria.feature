Feature: Administrador agrega usuario como administrador de una materia
    
    Scenario: MateriaController agrega admin a un usuario y una materia que existen este agrega al usuario a la lista de admins de la materia y responde un ok como ResponseEntity con body Materia Updated
        Given Un setUp MateriaController y UsuarioController mas utilidades
        And Materia "Estructura" existente
        And Materia "Obj 2" existente
        And Un Usuario con usuario "fidelito" password "1234" nombre "Fidel" y apellido "Castro"
        When MateriaController agrega administrador a "fidelito" en la materia "1"
        Then Se retorna un ResponseEntity ok con body Materia Updated y la materia "1" posee un admin que es este usuario

    Scenario: MateriaController al recibir un usuario y una materia que no existen se retorna un ResponseEntity not found con Datos Invalidos en el body
        Given Un setUp MateriaController y UsuarioController mas utilidades
        When MateriaController agrega administrador a "no existo" en una materia que no existe
        Then Se retorna un ResponseEntity not found con body Datos Invalidos

    Scenario: MateriaController al recibir un usuario y una materia que no existen se retorna un ResponseEntity not found con Datos Invalidos en el body
        Given Un setUp MateriaController y UsuarioController mas utilidades
        And Un Usuario con usuario "fidelito" password "1234" nombre "Fidel" y apellido "Castro"
        When MateriaController agrega administrador a "fidelito" en una materia que no existe
        Then Se retorna un ResponseEntity not found con body Datos Invalidos