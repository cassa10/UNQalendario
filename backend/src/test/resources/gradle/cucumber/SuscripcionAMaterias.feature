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