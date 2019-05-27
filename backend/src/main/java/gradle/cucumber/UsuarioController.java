package gradle.cucumber;

import Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
public class UsuarioController {

    private UsuarioService usuarioService = new UsuarioService();

    @RequestMapping(method = RequestMethod.GET, value = "/usuario/{idUsuario}")
    public Usuario get(@PathVariable String idUsuario) {
        return this.usuarioService.get(idUsuario);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usuario")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        this.usuarioService.guardarUsuario(usuario);
        return usuario;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verificar")
    public ResponseEntity checkerSiExisteUsuarioYPassword(@RequestBody HashMap<String, String> data) {
        String usuario = data.get("usuario");
        String password = data.get("password");
        if (this.usuarioService.verificarUsuarioValido(usuario, password)) {
            return ResponseEntity.ok(this.usuarioService.getUsuarioPorNombre(usuario).getId());
        } else {
            return new ResponseEntity<>("Datos Invalidos", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/suscribir/{idMateria}")
    public ResponseEntity suscribirAMateria(@PathVariable String idMateria, @RequestBody String idUsuario){
        this.usuarioService.suscribirA(idUsuario, idMateria);
        return ResponseEntity.ok(idUsuario + idMateria);
    }
}