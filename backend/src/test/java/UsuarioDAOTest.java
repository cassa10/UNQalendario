import gradle.cucumber.Persistence.UsuarioDAO;
import gradle.cucumber.Service.UNQalendarioService;
import gradle.cucumber.Tarea;
import gradle.cucumber.Usuario;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOTest extends TestCase {

    private Usuario usuario1, usuario2;
    private UNQalendarioService UNQalendarioService;
    private UsuarioDAO usuarioDAO;
    private Tarea notificacion1, notificacion2;
    private List<Usuario> usuarios;

    @Before
    public void setUp(){
        UNQalendarioService = new UNQalendarioService();
        usuarioDAO = new UsuarioDAO();

        usuario1 = new Usuario("cesar","123","Cesar","Tec");
        usuario2 = new Usuario("pepe","123","Pepe","Grillo");


        usuarioDAO.save(usuario1);
        usuarioDAO.save(usuario2);

        usuario1 = usuarioDAO.getUsuarioPorNombre(usuario1.getNombreUsuario());
        usuario2 = usuarioDAO.getUsuarioPorNombre(usuario2.getNombreUsuario());

        usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        notificacion1 = new Tarea("Parcial", LocalDate.of(2020,1,2));
        notificacion2 = new Tarea("Tarea Normal",LocalDate.of(2020,1,1));
    }

    @After
    public void tearDown(){
        this.UNQalendarioService.destroy();
    }

    @Test
    public void testAgregarNotificacion(){
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(notificacion1);


        //AGREGANDO Notificacion 1
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion1,false);

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().containsAll(tareas));

        assertEquals(usuario2.getNotificaciones().size(),1);
        assertTrue(usuario2.getNotificaciones().containsAll(tareas));

        //AGREGANDO Notificacion 2

        tareas.add(notificacion2);

        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion2,false);

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),2);
        assertTrue(usuario1.getNotificaciones().containsAll(tareas));

        assertEquals(usuario2.getNotificaciones().size(),2);
        assertTrue(usuario2.getNotificaciones().containsAll(tareas));
    }

    @Test
    public void testAgregarDosNotificacionesYLuegoBorrarlas(){

        //AGREGANDO LAS NOTIFICACIONES
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion1,false);
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion2,false);

        //BORRANDO NOTIFICACION2
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion2,true);

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().contains(notificacion1));

        assertEquals(usuario2.getNotificaciones().size(),1);
        assertTrue(usuario2.getNotificaciones().contains(notificacion1));

        //BORRANDO UNA NOTIFICACION QUE NO EXISTE NO HACE NADA
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion2,true);

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().contains(notificacion1));

        assertEquals(usuario2.getNotificaciones().size(),1);
        assertTrue(usuario2.getNotificaciones().contains(notificacion1));


        //BORRANDO LA NOTIFICACION 1
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, notificacion1,true);

        this.actualizarUsuariosDeLaDB();

        assertTrue(usuario1.getNotificaciones().isEmpty());

        assertTrue(usuario2.getNotificaciones().isEmpty());

    }

    private void actualizarUsuariosDeLaDB(){
        usuario1 = usuarioDAO.getUsuarioPorNombre(usuario1.getNombreUsuario());
        usuario2 = usuarioDAO.getUsuarioPorNombre(usuario2.getNombreUsuario());
    }
}
