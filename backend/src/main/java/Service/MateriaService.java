package Service;

import Persistence.MateriaDAO;
import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;

import java.util.List;

public class MateriaService {
    private MateriaDAO materiaDAO;

    public MateriaService() {
        this.materiaDAO = new MateriaDAO();
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
}
