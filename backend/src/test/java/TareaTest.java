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
}
