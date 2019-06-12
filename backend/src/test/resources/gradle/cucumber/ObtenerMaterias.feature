Feature: Obtener Materias

    Scenario: Materia Controller no retorna materias cuando se le pide todas las materias ya que no existen materias
        Given Una Materia Controller
        When Se solicita todas las materias a la Materia Controller
        Then Se retorna una lista vacia osea ninguna materia

    Scenario: Materia Controller retorna como todas las materias retorna unicamente 2 materias siendo las unicas
        Given Una Materia Controller
        And Materia "Intro" en el sistema
        And Materia "Orga" en el sistema
        When Se solicita todas las materias a la Materia Controller
        Then Se retornan las "2" materias anteriores

    Scenario: Al pedirle a la Materia Controller getMateria con el id de la primera materia existente este devuelve la materia
        Given Una Materia Controller
        And Materia "Intro" en el sistema
        And Materia "Orga" en el sistema
        When Se solicita get materia con el id de la materia "1" dada a la Materia Controller
        Then Se retorna la materia "1" ya que existe

    Scenario: Al pedirle a la Materia Controller getMateria con el id de la segundo materia existente este devuelve la materia
        Given Una Materia Controller
        And Materia "Intro" en el sistema
        And Materia "Orga" en el sistema
        And Materia "Mate2" en el sistema
        When Se solicita get materia con el id de la materia "2" dada a la Materia Controller
        Then Se retorna la materia "2" ya que existe

    Scenario: Al pedirle a la Materia Controller buscarMaterias con el string "Intro" este devuelve las materias que contengan ese string en su nombre
        Given Una Materia Controller
        And Materia "Intro" en el sistema
        And Materia "Orga" en el sistema
        And Materia "Intro 2" en el sistema
        And Materia "Mate2" en el sistema
        When Se solicita buscarMateria con el string de "Intro" a la Materia Controller
        Then Se retornan las materias que contengan "Intro" en su nombre

    Scenario: Al pedirle a la Materia Controller buscarMaterias con el string "" este devuelve todas las materias
        Given Una Materia Controller
        And Materia "Intro" en el sistema
        And Materia "Orga" en el sistema
        And Materia "Mate2" en el sistema
        When Se solicita buscarMateria con el string de "" a la Materia Controller
        Then Se retornan las materias que contengan "" en su nombre

    Scenario: Al pedirle a la Materia Controller buscarMaterias con el string "MATERIAQUENOEXISTE" este no devuelve nada
        Given Una Materia Controller
        And Materia "Intro" en el sistema
        And Materia "Orga" en el sistema
        And Materia "Mate2" en el sistema
        When Se solicita buscarMateria con el string de "MATERIAQUENOEXISTE" a la Materia Controller
        Then Se retornan las materias que contengan "MATERIAQUENOEXISTE" en su nombre