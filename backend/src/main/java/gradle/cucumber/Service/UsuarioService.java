package gradle.cucumber.Service;

import gradle.cucumber.Persistence.MateriaDAO;
import gradle.cucumber.Persistence.UsuarioDAO;
import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;

import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;
    private MateriaDAO materiaDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.materiaDAO = new MateriaDAO();
    }

    public Usuario get(String idUsuario) {
        return this.usuarioDAO.get(idUsuario);
    }

    public void guardarUsuario(Usuario usuario) {

        this.usuarioDAO.save(usuario);
    }

    public boolean verificarUsuarioValido(String usuario, String password) {
        return this.usuarioDAO.verificarUsuarioValido(usuario,password);
    }

    public Usuario getUsuarioPorNombre(String usuario) {

        return this.usuarioDAO.getUsuarioPorNombre(usuario);
    }

    public void suscribirA(String idUsuario, String idMateria) {
        Usuario usuarioRecuperado = this.usuarioDAO.get(idUsuario);
        Materia materiaRecuperado = this.materiaDAO.get(idMateria);

        materiaRecuperado.agregarSuscriptor(usuarioRecuperado);

        this.materiaDAO.suscribirUsuario(idMateria, materiaRecuperado.getSuscriptores());
    }

    public List<Materia> getMaterias(String idUsuario) {
        return this.materiaDAO.getMateriasDe(idUsuario);
    }

    public void borrarUsuarios(){
        this.usuarioDAO.deleteAll();
    }

    public boolean existeNombreUsuario(String usuarioNombre) {
        return this.usuarioDAO.existeUsuario(usuarioNombre);
    }

    public boolean existeUsuarioId(String idUsuario) {
        return this.usuarioDAO.existeUsuarioId(idUsuario);
    }

    public void desuscribirDe(String idUsuario, String idMateria) {
        Usuario usuarioRecuperado = this.usuarioDAO.get(idUsuario);
        Materia materiaRecuperado = this.materiaDAO.get(idMateria);

        materiaRecuperado.eliminarSuscriptor(usuarioRecuperado);

        this.materiaDAO.desuscribirUsuario(idMateria, materiaRecuperado.getSuscriptores());
    }

    public void borrarNotifiacionesNuevas(String idUsuario) {
        Usuario usuarioRecuperado = this.usuarioDAO.get(idUsuario);
        usuarioRecuperado.seVieronTodasLasNotifiaciones();

        this.usuarioDAO.noHayNotificacionesVistas(idUsuario);
    }

    public void borrarNotificacion(String idUsuario, String idTarea) {
        Usuario usuarioRecuperado = this.usuarioDAO.get(idUsuario);
        usuarioRecuperado.eliminarNotificacion(idTarea);

        this.usuarioDAO.actualizarNotifiaciones(idUsuario, usuarioRecuperado.getNotificaciones());
    }
}