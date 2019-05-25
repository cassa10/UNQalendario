

import Persistence.DummyDAO;
import Persistence.DummyObject;
import junit.framework.TestCase;
import org.junit.Test;

public class MongoTest extends TestCase {
    @Test
    public void testSavingAnObjectWithMongo() {
        DummyDAO mongoDAO = new DummyDAO(DummyObject.class);
        DummyObject sth = new DummyObject("0001");
        mongoDAO.save(sth);

        DummyObject recovered = mongoDAO.get(sth.getId());

        assertEquals(sth.getId(),recovered.getId());
        assertEquals(sth.getCodigo(),recovered.getCodigo());
    }
}
