package Persistence;

import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;

public class UsuarioDAO extends GenericMongoDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }
}
