package gradle.cucumber;

import Service.MateriaService;

import Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@CrossOrigin
@RestController
public class MateriaController {

    private MateriaService gestorMaterias = new MateriaService();
    private UsuarioService gestorUsuario = new UsuarioService();

    @RequestMapping(method = RequestMethod.GET, value = "/materia/{idMateria}")
    public Materia getMateria(@PathVariable String idMateria) {
        return this.gestorMaterias.get(idMateria);
    }

    @RequestMapping(method = RequestMethod.GET, value="/materias")
    public Iterable<Materia> todasLasMaterias() { return gestorMaterias.getMaterias(); }

    @RequestMapping(method = RequestMethod.POST, value="/materia")
    public Materia save(@RequestBody Materia materia) {
        gestorMaterias.agregarMateria(materia);
        return materia;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/search/materia/{nombre}")
    public Iterable<Materia> buscarMaterias(@PathVariable String nombre) {
        return gestorMaterias.filtrarMateriasPorNombre(nombre);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/tarea/{idMateria}")
    public ResponseEntity agregarTareaEnMateria(@PathVariable String idMateria, @RequestBody HashMap<String, String> body){
        if(! this.gestorMaterias.existeMateriaConId(idMateria)){
            return new ResponseEntity<>("Path Invalido", HttpStatus.NOT_FOUND);
        }

        String idUsuario = body.get("usuario");
        if(! this.gestorMaterias.elUsuarioEsAdminDeMateria(idMateria,idUsuario)){
            return new ResponseEntity<>("Usuario Invalido",HttpStatus.NOT_FOUND);
        }

        String fechaString = body.get("fechaEntrega");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Tarea tarea = new Tarea(body.get("nombreTarea"),LocalDate.parse(fechaString, formatter));
        gestorMaterias.agregarTarea(idMateria,tarea);

        return ResponseEntity.ok(gestorMaterias.get(idMateria));
    }

    @RequestMapping(method = RequestMethod.POST, value ="/administracion/{idMateria}")
    public ResponseEntity agregarAdministradorAUnaMateria(@PathVariable String idMateria, @RequestBody HashMap<String, String> data){
        Materia materia = this.gestorMaterias.get(idMateria);
        String usuarioNombre = data.get("usuario");
        if(this.gestorUsuario.existeNombreUsuario(usuarioNombre) || this.gestorMaterias.existeMateriaConId(idMateria)){
            Usuario usuario = gestorUsuario.getUsuarioPorNombre(usuarioNombre);
            gestorMaterias.agregarAdministrador(materia, usuario);
            return ResponseEntity.ok("Materia Updated");
        }else{
            return new ResponseEntity<>("Datos Invalidos", HttpStatus.NOT_FOUND);
        }
    }
}
