package Service;

import Persistence.MateriaDAO;
import Persistence.UsuarioDAO;
import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;

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
        return this.usuarioDAO.find("{ nombre: # , password: #}", usuario , password).size() == 1;
    }

    public Usuario getUsuarioPorNombre(String usuario) {
        return this.usuarioDAO.find("{ nombre: # }", usuario).get(0);
    }

    public void suscribirA(String idUsuario, String idMateria) {
        Usuario usuarioRecuperado = this.usuarioDAO.get(idUsuario);
        Materia materiaRecuperado = this.materiaDAO.get(idMateria);

        usuarioRecuperado.agregarMateriaSubscrita(materiaRecuperado);

        this.usuarioDAO.suscribirUsuario(idUsuario, usuarioRecuperado.getMateriasSuscritas());
    }
}