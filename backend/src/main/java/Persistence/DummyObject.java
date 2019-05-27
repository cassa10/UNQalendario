package Persistence;


import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Objects;


public class DummyObject {

    @MongoId
    @MongoObjectId
    private String id;

    private String codigo;

    public DummyObject(String codigo) {
        this.codigo = codigo;
    }

    protected DummyObject() {
    }

    public String getId() {
        return this.id;
    }

    public String getCodigo() {
        return this.codigo;
    }
}
