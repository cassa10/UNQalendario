import gradle.cucumber.Materia;
import gradle.cucumber.Usuario;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class MateriaTest extends TestCase {

    private Materia materia;
    private Usuario usuario1;
    private Usuario usuario2;

    @Before
    public void setUp(){
        materia = new Materia("Intro");
        usuario1 = new Usuario("cesar","123","Cesar","Tec");
        usuario2 = new Usuario("dog","perro","Guau","Perro");

    }

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

    @Test
    public void testAgregarSuscriptorNoAgregaDosVecesAlMismoUsuario(){
        materia.agregarSuscriptor(usuario1);
        materia.agregarSuscriptor(usuario1);

        assertEquals(materia.getSuscriptores().size(),1);
        assertTrue(materia.getSuscriptores().contains(usuario1));

        materia.agregarSuscriptor(usuario2);

        assertEquals(materia.getSuscriptores().size(),2);
        assertTrue(materia.getSuscriptores().contains(usuario1));
        assertTrue(materia.getSuscriptores().contains(usuario2));

    }

    @Test
    public void testAgregarAdministradorNoAgregaDosVecesAlMismoUsuario(){
        materia.agregarAdministrador(usuario1);
        materia.agregarAdministrador(usuario1);

        assertEquals(materia.getAdministradores().size(),1);
        assertTrue(materia.getAdministradores().contains(usuario1));

        materia.agregarAdministrador(usuario2);

        assertEquals(materia.getAdministradores().size(),2);
        assertTrue(materia.getAdministradores().contains(usuario1));
        assertTrue(materia.getAdministradores().contains(usuario2));
    }

    @Test
    public void testGetNombreDelDocenteDeUnaMateriaSinAdministradoresEsUnStringVacio(){
        assertEquals(this.materia.getNombreDelDocente(),"");
    }

    @Test
    public void testGetNombreDelDocenteDeUnaMateriaConAdministradoresSiempreTomaAlPrimeroDeLaListaYDevuelveSuNombreMasApellido(){
        this.materia.agregarAdministrador(usuario1);
        this.materia.agregarAdministrador(usuario2);

        assertEquals(this.materia.getNombreDelDocente(),usuario1.getNombreYApellido());
    }

}
