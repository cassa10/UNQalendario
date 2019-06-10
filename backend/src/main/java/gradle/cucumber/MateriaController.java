package gradle.cucumber;

import Service.MateriaService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@CrossOrigin
@RestController
public class MateriaController {

    private MateriaService gestorMaterias = new MateriaService();

    @RequestMapping(method = RequestMethod.GET, value = "/materia/{idMateria}")
    public Materia getMateria(@PathVariable String idMateria) {
        return this.gestorMaterias.get(idMateria);
    }

    @RequestMapping(method = RequestMethod.GET, value="/materias")
    public Iterable<Materia> todasLasMaterias() {return  gestorMaterias.getMaterias();}

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

}
