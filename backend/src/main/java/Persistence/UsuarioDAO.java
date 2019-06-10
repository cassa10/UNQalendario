package Persistence;

import gradle.cucumber.Usuario;
import org.bson.types.ObjectId;

import java.util.List;

public class UsuarioDAO extends GenericMongoDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario getUsuarioPorNombre(String usuario) {

        return this.find("{ nombre: # }", usuario).get(0);
    }

    public boolean existeUsuario(String usuario){
        return ! this.find("{ nombre: # }", usuario).isEmpty();
    }

    public boolean verificarUsuarioValido(String usuario, String password) {
        return this.find("{ nombre: # , password: #}", usuario , password).size() == 1;
    }

    public boolean existeUsuarioId(String idUsuario) {
        ObjectId idMongo = new ObjectId(idUsuario);
        return ! this.find("{ _id: # }", idMongo).isEmpty();
    }
}
