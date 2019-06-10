package gradle.cucumber;

public class UsuarioYaExiste extends Exception {

    public UsuarioYaExiste(){
        super("Usuario Ya Existe");
    }
}
