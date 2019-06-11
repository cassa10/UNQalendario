import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;
import junit.framework.TestCase;
import org.junit.Test;

public class MateriaTest extends TestCase {
    @Test
    public void testCuandoAsignoUnProfesorComoAdministradorEsteAhoraFiguraEnLaMateria(){
        Materia materia = new Materia();
        Usuario docente = new Usuario("cesar","123","Cesar","");
        materia.agregarAdministrador(docente);

        assertTrue(materia.getAdministradores().contains(docente));
    }
    @Test
    public void testCuandoCreoUnaMateriaConNombreDeAdministradorMiguelAhoraElNombreDeAdministradorEsMiguel(){
        Materia materia = new Materia("Corte y confeccion");
        Usuario docente = new Usuario("miguelito","123","Miguel","");
        materia.agregarAdministrador(docente);

        assertEquals(materia.getNombreDelDocente(),"Miguel");
    }
}
