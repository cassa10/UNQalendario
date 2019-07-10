package gradle.cucumber.Persistence;

import gradle.cucumber.Materia;
import gradle.cucumber.Notificacion;
import gradle.cucumber.Tarea;
import gradle.cucumber.Usuario;
import org.bson.types.ObjectId;

import java.util.List;

public class UsuarioDAO extends GenericMongoDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario getUsuarioPorNombre(String usuarioNombre) {

        return this.find("{ nombreUsuario: # }", usuarioNombre).get(0);
    }

    public boolean existeUsuario(String usuarioNombre){
        return ! this.find("{ nombreUsuario: # }", usuarioNombre).isEmpty();
    }

    public boolean verificarUsuarioValido(String usuarioNombre, String password) {
        return this.find("{ nombreUsuario: # , password: #}", usuarioNombre , password).size() == 1;
    }

    public boolean existeUsuarioId(String idUsuario) {
        ObjectId idMongo = new ObjectId(idUsuario);
        return ! this.find("{ _id: # }", idMongo).isEmpty();
    }

    public void updateNotificacionesDeUsuarios(List<Usuario> usuarios, Tarea tarea, boolean esBorrarTarea, Materia materia) {
        usuarios.forEach(user -> this.updateNotificaciones(user,tarea, esBorrarTarea, materia));
    }

    private void updateNotificaciones(Usuario user, Tarea tarea, boolean esBorrarTarea, Materia materia) {
        Usuario usuarioRecuperado = this.get(user.getId());

        if(esBorrarTarea){
            usuarioRecuperado.eliminarNotificacion(tarea.getId());
        }else{
            Notificacion notificacion = new Notificacion(materia.getId(), tarea, materia.getNombre());
            usuarioRecuperado.agregarNotificacion(notificacion);
        }

        ObjectId objectId = new ObjectId(user.getId());

        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { notificaciones: # }}", usuarioRecuperado.getNotificaciones());
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { nuevasNotificaciones: # }}", usuarioRecuperado.getNuevasNotificaciones());
    }

    public void noHayNotificacionesVistas(String idUsuario) {
        ObjectId objectId = new ObjectId(idUsuario);
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { nuevasNotificaciones: 0 }}");
    }

    public void actualizarNotifiaciones(String idUsuario, List<Notificacion> notificaciones) {
        ObjectId objectId = new ObjectId(idUsuario);
        this.mongoCollection.update("{ _id: # }", objectId).with("{ $set: { notificaciones: # }}", notificaciones);
    }
}
