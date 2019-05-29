package Persistence;

import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;
import org.bson.types.ObjectId;

import java.util.List;

public class UsuarioDAO extends GenericMongoDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public void suscribirUsuario(String idUsuario, List<Materia> materias) {
        ObjectId objectId = new ObjectId(idUsuario);
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { materiasSuscritas: # }}", materias);
    }
}
