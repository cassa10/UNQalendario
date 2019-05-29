package Persistence;

import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;
import org.bson.types.ObjectId;

import java.util.List;

public class MateriaDAO extends GenericMongoDAO<Materia> {
    public MateriaDAO() {
        super(Materia.class);
    }

    public void suscribirUsuario(String idMateria, List<Usuario> suscriptores) {
        ObjectId objectId = new ObjectId(idMateria);
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { suscriptores: # }}", suscriptores);
    }

    public List<Materia> getMateriasDe(String idUsuario) {
        ObjectId objectId = new ObjectId(idUsuario);
        return this.find("{ suscriptores._id: # }", objectId);
    }
}
