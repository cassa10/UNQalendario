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

        if(this.gestorMaterias.laTareaExisteEnMateria(idMateria,tarea)){
            return new ResponseEntity<>("La tarea ya existe",HttpStatus.BAD_REQUEST);
        }

        gestorMaterias.agregarTarea(idMateria,tarea);

        return ResponseEntity.ok(gestorMaterias.get(idMateria));
    }

    @RequestMapping(method = RequestMethod.POST, value ="/administracion/{idMateria}")
    public ResponseEntity agregarAdministradorAUnaMateria(@PathVariable String idMateria, @RequestBody HashMap<String, String> data){
        String usuarioNombre = data.get("usuario");

        if(! (this.gestorMaterias.existeMateriaConId(idMateria) && this.gestorUsuario.existeNombreUsuario(usuarioNombre)) ){
            return new ResponseEntity<>("Datos Invalidos",HttpStatus.NOT_FOUND);
        }

        Materia materia = this.gestorMaterias.get(idMateria);
        Usuario usuario = gestorUsuario.getUsuarioPorNombre(usuarioNombre);
        gestorMaterias.agregarAdministrador(materia, usuario);
        return ResponseEntity.ok("Materia Updated");
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value ="/tarea/{idMateria}")
    public ResponseEntity eliminarTareaEnMateria(@PathVariable String idMateria, @RequestBody HashMap<String, String> body) {

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

        gestorMaterias.eliminarTarea(idMateria,tarea);

        return ResponseEntity.ok(gestorMaterias.get(idMateria));
    }
}
