Feature: Desuscripcion de materias
    
    Scenario: Dado un usuario suscrito a una materia y luego se desuscribe y la materia no lo posee en su lista de suscriptores
        Given Un setUp nuevo de UNQalendario
        And Un Nuevo Usuario "pepe" y password "123"
        And Una nueva Materia con el nombre "Mate 2"
        And Este Usuario suscrito a la materia anterior
        When Este Usuario se desuscribe de esta materia
        Then Este Usuario ya no esta suscrito a esta materia

    Scenario: Dado unos usuarios suscriptos a una materia y al desuscribirse el primero de ellos, este solo desaparece de su lista de suscriptores
        Given Un setUp nuevo de UNQalendario
        And Un Nuevo Usuario "pepe" y password "123"
        And Otro nuevo Usuario "bebe" y password "321"
        And Otro nuevo Usuario "loop" y password "asd"
        And Una nueva Materia con el nombre "Mate 2"
        And Todos los Usuarios anteriores suscritos a la materia anterior
        When El primer Usuario se desuscribe de esta materia
        Then El primer Usuario ya no esta suscrito a esta materia y los demas si
