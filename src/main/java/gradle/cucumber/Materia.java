package gradle.cucumber;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.NotNull;

public class Materia {
    @MongoId
    @MongoObjectId
    private String id;
    private String nombre;

    public Materia(){}
    public Materia(String nombre){this.nombre = nombre;}

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
