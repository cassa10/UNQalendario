package Persistence;

import gradle.cucumber.Materia;
import gradle.cucumber.Tarea;
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

    public void agregarTareas(String idMateria, List<Tarea> tareas) {
        ObjectId objectId = new ObjectId(idMateria);
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { tareas: # }}", tareas);
    }

    public void agregarAdministradores(Materia materia, List<Usuario> administradores) {
        ObjectId objectId = new ObjectId(materia.getId());
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { administradores: # }}", administradores);
    }

    public boolean existeMateriaConId(String idMateria) {
        ObjectId objectId = new ObjectId(idMateria);
        return !this.find("{ _id: # }", objectId).isEmpty();
    }

    public boolean existeAdministradorEnMateria(String idMateria, String idUsuario) {
        return this.get(idMateria).getAdministradores().stream().anyMatch(adm -> adm.getId().equals(idUsuario));
    }

    public void updateUsuarios(String idMateria, List<Usuario> suscriptores) {
        this.mongoCollection.update("{ _id: # }", idMateria).with("{ $set: { suscriptores: # }}", suscriptores);
    }
}
