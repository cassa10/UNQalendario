import gradle.cucumber.Materia;
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
    private Tarea tarea1, tarea2;
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

        tarea1 = new Tarea("Parcial", LocalDate.of(2020,1,2));
        tarea1.setId("1");
        tarea2 = new Tarea("Tarea Normal",LocalDate.of(2020,1,1));
        tarea2.setId("2");
    }

    @After
    public void tearDown(){
        this.UNQalendarioService.destroy();
    }

    @Test
    public void testAgregarNotificacion(){
        //AGREGANDO Notificacion 1
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea1,false, new Materia());

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea1.getId())));

        assertEquals(usuario2.getNotificaciones().size(),1);
        assertTrue(usuario2.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea1.getId())));

        //AGREGANDO Notificacion 2

        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea2,false, new Materia());

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),2);
        assertTrue(usuario1.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea2.getId())));

        assertEquals(usuario2.getNotificaciones().size(),2);
        assertTrue(usuario2.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea2.getId())));
    }

    @Test
    public void testAgregarDosNotificacionesYLuegoBorrarlas(){

        //AGREGANDO LAS NOTIFICACIONES
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea1,false, new Materia());
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea2,false, new Materia());

        //BORRANDO NOTIFICACION2
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea2,true, new Materia());

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea1.getId())));

        assertEquals(usuario2.getNotificaciones().size(),1);
        assertTrue(usuario2.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea1.getId())));

        //BORRANDO UNA NOTIFICACION QUE NO EXISTE NO HACE NADA
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea2,true, new Materia());

        this.actualizarUsuariosDeLaDB();

        assertEquals(usuario1.getNotificaciones().size(),1);
        assertTrue(usuario1.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea1.getId())));

        assertEquals(usuario2.getNotificaciones().size(),1);
        assertTrue(usuario2.getNotificaciones().stream().anyMatch(notificacion -> notificacion.getTarea().getId().equals(tarea1.getId())));


        //BORRANDO LA NOTIFICACION 1
        usuarioDAO.updateNotificacionesDeUsuarios(usuarios, tarea1,true, new Materia());

        this.actualizarUsuariosDeLaDB();

        assertTrue(usuario1.getNotificaciones().isEmpty());

        assertTrue(usuario2.getNotificaciones().isEmpty());

    }

    private void actualizarUsuariosDeLaDB(){
        usuario1 = usuarioDAO.getUsuarioPorNombre(usuario1.getNombreUsuario());
        usuario2 = usuarioDAO.getUsuarioPorNombre(usuario2.getNombreUsuario());
    }
}
