import gradle.cucumber.Tarea;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;

public class TareaTest extends TestCase {

    @Test
    public void testGetterTarea(){
        LocalDate fecha = LocalDate.of(2019,11,18);
        Tarea tarea = new Tarea("Tarea 1",fecha);

        assertEquals(tarea.getNombre(),"Tarea 1");
        assertEquals(tarea.getFecha(), fecha);
    }

    @Test
    public void testEqualsTarea(){
        LocalDate fecha = LocalDate.of(2019,11,18);
        Tarea tarea1 = new Tarea("Nueva Tarea",fecha);

        LocalDate fecha2 = LocalDate.of(2029,11,19);
        Tarea tarea2 = new Tarea("Nueva Tarea",fecha2);

        Tarea tarea3 = new Tarea("Nueva Tarea 3",fecha);

        assertEquals(tarea1,tarea1);
        assertNotSame(tarea1,tarea2);
        assertNotSame(tarea1,tarea3);
        assertNotSame(tarea2,tarea3);

        assertNotSame(tarea1,null);
        assertNotSame(null,tarea2);

        assertNotSame(tarea1,fecha);
    }
}
