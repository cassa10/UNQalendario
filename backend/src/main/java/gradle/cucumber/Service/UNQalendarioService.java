package gradle.cucumber.Service;

public class UNQalendarioService {
    private MateriaService materiaService = new MateriaService();
    private UsuarioService usuarioService = new UsuarioService();


    public void destroy(){
        materiaService.borrarMaterias();
        usuarioService.borrarUsuarios();
    }
}
