Feature: RegistroYInicioSesionDeUsuario
    


 Scenario: Un Usuario se registra y se guarda en la base de datos
    Given Un UsuarioController y un UsuarioService
    And Base de Datos con Usuarios vacia
    And Un Usuario llamado "Pepito" con password "123"
    When El UsuarioController guarda a este usuario
    Then El Usuario aparece en la coleccion de Usuarios de la base de datos

 Scenario: Al solicitar al UsuarioController un Get de su id y devuelve su Usuario
    Given Un UsuarioController y un UsuarioService
    And El Usuario "Pepito" con password "123" en la Base de Datos
    When El UsuarioController se le hace un get del id de este usuario
    Then Este metodo del UsuarioController devuelve al Usuario con ese id