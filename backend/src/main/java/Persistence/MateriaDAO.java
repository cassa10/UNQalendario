package Persistence;

import gradle.cucumber.Materia;

public class MateriaDAO extends GenericMongoDAO<Materia> {
    public MateriaDAO() {
        super(Materia.class);
    }
}
