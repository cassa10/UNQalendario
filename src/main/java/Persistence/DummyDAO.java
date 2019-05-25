package Persistence;

public class DummyDAO extends GenericMongoDAO<DummyObject> {

    public DummyDAO(Class<DummyObject> entityType) {
        super(entityType);
    }
}
