package Service;

import Persistence.MateriaDAO;
import gradle.cucumber.Materia;

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
}
