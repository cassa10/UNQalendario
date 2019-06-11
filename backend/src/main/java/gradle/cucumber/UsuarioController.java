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
    public Usuario guardarUsuario(@RequestBody Usuario usuario) throws UsuarioYaExiste{
       if(usuarioService.existeNombreUsuario(usuario.getNombreUsuario())) {
         throw new UsuarioYaExiste();
       }else{
           this.usuarioService.guardarUsuario(usuario);
           return usuario;
       }
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
    public ResponseEntity suscribirAMateria(@PathVariable String idMateria, @RequestBody HashMap<String, String> data){
        this.usuarioService.suscribirA(data.get("idUsuario"), idMateria);
        return ResponseEntity.ok(this.usuarioService.getMaterias(data.get("idUsuario")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/materias/{idUsuario}")
    public ResponseEntity getMaterias(@PathVariable String idUsuario) {
        if(! this.usuarioService.existeUsuarioId(idUsuario)){
           return new ResponseEntity<>("El Usuario con el id " + idUsuario + " no existe", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(this.usuarioService.getMaterias(idUsuario));
        }
    }
}