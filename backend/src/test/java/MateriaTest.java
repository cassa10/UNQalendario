import gradle.cucumber.Docente;
import gradle.cucumber.Materia;
import junit.framework.TestCase;
import org.junit.Test;

public class MateriaTest extends TestCase {
    @Test
    public void testCuandoAsignoUnProfesorComoAdministradorEsteAhoraFiguraEnLaMateria(){
        Materia materia = new Materia();
        Docente docente = new Docente("Cesar de redes");
        materia.agregarAdministrador(docente);

        assertTrue(materia.admins().contains(docente));
    }
}
