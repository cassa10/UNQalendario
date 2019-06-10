Feature: Suscripcion a las materias
    
 Scenario: Un Usuario se suscribe a una Materia
    Given Un setUp de UNQalendario
    And El Usuario llamado "Jorgito" con password "321"
    And Una Materia "Mate2" sin suscriptores
    When Este Usuario se suscribe a la anterior materia
    Then La materia posee un suscriptor cuyo usuario es el anteriormente suscrito


 Scenario: Tres Usuarios se suscriben a una Materia
    Given Un setUp de UNQalendario
    And Unos de los Usuarios llamado "Jorgito" con password "321"
    And Unos de los Usuarios llamado "Ern3st0" con password "xD"
    And Unos de los Usuarios llamado "Perro" con password "p3rr0"
    And Una Materia "Mate2" sin suscriptores
    When Estos Usuarios se suscriben a la anterior materia
    Then La materia posee "3" suscriptores cuyos usuarios son los anteriormente suscritos

 Scenario: UsuarioController obtiene todas las materias en las que esta suscrito Pepe en el body de un responseEntity Ok
    Given Un setUp de UNQalendario
    And El Usuario llamado "Pepe" con password "123"
    And Una de las Materias "Mate2" donde esta suscrito el usuario anterior
    And Una de las Materias "Orga" donde esta suscrito el usuario anterior
    When UsuarioController obtiene todas las materias suscritas del usuario anterior
    Then UsuarioController responde un ResponseEntity Ok con todas las materias suscritas del usuario anterior en su body

 Scenario: UsuarioController retorna un ResponseEntity Not Found con un El Usuario con el Id No Existe en el body
    Given Un setUp de UNQalendario
    And Una Materia "Mate2" sin suscriptores
    And Una Materia "Intro" sin suscriptores
    When UsuarioController intenta obtener las materias suscritas de un usuario que no existe
    Then UsuarioController responde un ResponseEntity Not Found una descripcion de El Usuario con el Id no existe en su body

 Scenario: UsuarioController obtiene unicamente las materias en las que esta suscrito Pepe en el body de un responseEntity Ok
    Given Un setUp de UNQalendario
    And El Usuario llamado "Pepe" con password "123"
    And Una Materia "Intro" sin suscriptores
    And Una de las Materias "Mate2" donde esta suscrito el usuario anterior
    And Una de las Materias "Orga" donde esta suscrito el usuario anterior
    When UsuarioController obtiene todas las materias suscritas del usuario anterior
    Then UsuarioController responde un ResponseEntity Ok con todas las materias suscritas del usuario anterior en su body

Scenario: UsuarioController no obtiene ninguna materia ya que Pepe no esta suscrito a ninguna materia y Responde con Un ResponseEntity Ok con una lista vacia en el body
    Given Un setUp de UNQalendario
    And El Usuario llamado "Pepe" con password "123"
    And Una Materia "Intro" sin suscriptores
    And Una Materia "Mate2" sin suscriptores
    And Una Materia "Orga" sin suscriptores
    When UsuarioController obtiene todas las materias suscritas del usuario anterior
    Then UsuarioController responde un ResponseEntity Ok con ninguna materia en su body