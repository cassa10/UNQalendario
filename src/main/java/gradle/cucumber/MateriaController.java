package gradle.cucumber;

import Service.MateriaService;
import gradle.cucumber.Materia;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MateriaController {
    MateriaService gestorMaterias = new MateriaService();

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
}
