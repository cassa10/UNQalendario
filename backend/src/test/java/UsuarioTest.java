import gradle.cucumber.Tarea;
import gradle.cucumber.Usuario;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class UsuarioTest extends TestCase {

    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;
    private Usuario usuario4;
    private Tarea tarea1,tarea2;

    @Before
    public void setUp(){
        usuario1 = new Usuario("cesar","123","Cesar","Tec");
        usuario2 = new Usuario("dog","perro","Guau","");
        usuario3 = new Usuario("boca","xD","","Boquita");
        usuario4 = new Usuario("keko","kk","","");
        tarea1 = new Tarea("Parcial", LocalDate.of(2020,1,2));
        tarea2 = new Tarea("Tarea Normal",LocalDate.of(2020,1,1));
    }

    @Test
    public void testGetPassword(){
        assertEquals(usuario1.getPassword(),"123");
        assertEquals(usuario2.getPassword(),"perro");
    }

    @Test
    public void testGetNombreYApellido(){
        assertEquals(usuario1.getNombreYApellido(),"Cesar, Tec");
        assertEquals(usuario2.getNombreYApellido(),"Guau");
        assertEquals(usuario3.getNombreYApellido(),"Boquita");
        assertEquals(usuario4.getNombreYApellido(),"");
    }

    @Test
    public void testGetNotificaciones(){
        usuario1.agregarNotificacion(tarea1);
        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().contains(tarea1));

        usuario1.agregarNotificacion(tarea2);

        assertEquals(usuario1.getNotificaciones().size(),2);
        assertTrue(usuario1.getNotificaciones().contains(tarea1));
        assertTrue(usuario1.getNotificaciones().contains(tarea2));
    }

}
