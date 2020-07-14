import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    UserManager um = new UserManager();

    @Test
    public void addNormalUserTest() {
        um.createNormalUser("Tom", "tom@hotmail.com", "123");
        assert um.usernameExists("Tom");
        assert um.emailExists("tom@hotmail.com");
        assert um.normalEmailPassword("tom@hotmail.com").equals("123");
    }

    @Test
    public void usernamePasswordTest() {
        um.createNormalUser("Tom", "tom@hotmail.com", "123");
        assertEquals("123", um.usernamePassword("Tom"));
    }

    @Test
    public void emailPasswordTest() {
        um.createNormalUser("Tom", "tom@hotmail.com", "123");
        assertEquals("123", um.emailPassword("tom@hotmail.com"));
    }


    @Test
    public void emailExistsTest() {
        um.createNormalUser("Tom", "tom@hotmail.com", "123");
        assertTrue(um.emailExists("tom@hotmail.com", false));
        assertFalse(um.emailExists("jerry@hotmail.com", true));

    }

    @Test
    public void usernameExistsTest() {
        um.createNormalUser("Tom", "tom@hotmail.com", "123");
        assertTrue(um.usernameExists("Tom"));
        assertFalse(um.usernameExists("Jerry"));
    }

    @Test
    public void addAdminUserTest() {
        um.createAdminUser("Jerry", "jerry@hotmail.com", "123");
        assert um.usernameExists("Jerry");
        assert um.emailExists("jerry@hotmail.com");
        assert um.adminEmailPassword("jerry@hotmail.com").equals("123");
    }

    @Test
    public void getNormalByTest() {
        um.createNormalUser("Tom", "tom@hotmail.com", "123");
        assertEquals("123", um.getNormalByUsername("Tom").getPassword());
        assertEquals("123", um.getNormalByEmail("tom@hotmail.com").getPassword());
    }

    @Test
    public void getAdminByTest() {
        um.createAdminUser("Jerry", "jerry@hotmail.com", "123");
        assertEquals("123", um.getAdminByUsername("Jerry").getPassword());
        assertEquals("123", um.getAdminByEmail("jerry@hotmail.com").getPassword());
    }

}
