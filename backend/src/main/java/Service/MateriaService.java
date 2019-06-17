package Service;

import Persistence.MateriaDAO;
import Persistence.UsuarioDAO;
import gradle.cucumber.Materia;
import gradle.cucumber.Tarea;
import gradle.cucumber.Usuario;

import java.util.List;

public class MateriaService {
    private MateriaDAO materiaDAO;
    private UsuarioDAO usuarioDAO;

    public MateriaService() {
        this.materiaDAO = new MateriaDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Materia> getMaterias() {
        return this.materiaDAO.find("");
    }

    public void agregarMateria(Materia materia) {
        this.materiaDAO.save(materia);
    }

    public Iterable<Materia> filtrarMateriasPorNombre(String nombre) {
        return this.materiaDAO.find("{ nombre : { $regex : \".*#.*\" } }", nombre);
    }

    public Materia get(String idMateria) {
        return this.materiaDAO.get(idMateria);
    }

    public void borrarMaterias(){
        this.materiaDAO.deleteAll();
    }

    public boolean tieneSuscriptor(Materia materia, Usuario usuario) {
        //TODO
        // REFACTOR MEJORAR A UNA QUERY DE MONGO

        return this.get(materia.getId()).getSuscriptores().contains(usuario);
    }

    public int cantidadSuscriptores(Materia materia) {
        //TODO
        // REFACTOR MEJORAR A UNA QUERY DE MONGO

        return this.get(materia.getId()).getSuscriptores().size();
    }

    public void agregarTarea(String idMateria, Tarea tarea) {

        Materia materia = materiaDAO.get(idMateria);
        materia.agregarTarea(tarea);

        List<Usuario> suscriptores = materia.getSuscriptores();

        usuarioDAO.updateNotificacionesDeUsuarios(suscriptores,tarea);
        materiaDAO.agregarTareas(idMateria,materia.getTareas());
    }

    public void agregarAdministrador(Materia materia, Usuario usuario){
        materia.agregarAdministrador(usuario);
        this.materiaDAO.agregarAdministradores(materia,materia.getAdministradores());
    }

    public boolean existeMateriaConId(String idMateria) {
        return this.materiaDAO.existeMateriaConId(idMateria);
    }

    public boolean elUsuarioEsAdminDeMateria(String idMateria, String idUsuario) {
        return this.materiaDAO.existeAdministradorEnMateria(idMateria,idUsuario);
    }
}
