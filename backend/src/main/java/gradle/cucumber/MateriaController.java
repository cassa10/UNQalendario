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
    //TODO
    // TESTIAR METEDOS EN GRIS(NO TESTEADO)

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
    public Materia agregarTareaEnMateria(@PathVariable String idMateria, @RequestBody HashMap<String, String> body){

        String fechaString = body.get("fechaEntrega");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        Tarea tarea = new Tarea(body.get("nombreTarea"),LocalDate.parse(fechaString, formatter));
        gestorMaterias.agregarTarea(idMateria,tarea);

        return gestorMaterias.get(idMateria);
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
