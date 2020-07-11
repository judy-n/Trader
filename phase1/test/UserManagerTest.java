import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    UserManager um = new UserManager();
    NormalUser User1 = new NormalUser("Tom", "tom@hotmail.com", "123");
    AdminUser User2 = new AdminUser("Jerry", "jerry@hotmail.com", "123", 0);

    @Test
    public void addNormalUserTest(){
        um.addUser(User1);
        assert um.getAllNormals().contains(User1);
        assert um.getAllUsers().contains(User1);
    }

    @Test
    public void usernamePasswordTest(){
        um.addUser(User1);
        assertEquals("123", um.usernamePassword("Tom"));
    }

    @Test
    public void emailPasswordTest(){
        um.addUser(User1);
        assertEquals("123", um.emailPassword("tom@hotmail.com"));
    }


    @Test
    public void emailExistsTest(){
        um.addUser(User1);
        assertTrue(um.emailExists("tom@hotmail.com", false));
        assertFalse(um.emailExists("jerry@hotmail.com", true));

    }

    @Test
    public void usernameExistsTest(){
        um.addUser(User1);
        assertTrue(um.usernameExists("Tom", false));
        assertFalse(um.usernameExists("Jerry", true));
    }

    @Test
    public void addAdminUserTest(){
        um.addUser(User2);
        assert um.getAllAdmins().contains(User2);
        assert um.getAllUsers().contains(User2);
    }

    @Test
    public void getNormalByTest(){
        um.addUser(User1);
        assertEquals(User1, um.getNormalByUsername("Tom"));
        assertEquals(User1, um.getNormalByEmail("tom@hotmail.com"));
    }
    @Test
    public void getAdminByTest(){
        um.addUser(User2);
        assertEquals(User2, um.getAdminByUsername("Jerry"));
        assertEquals(User2, um.getAdminByEmail("jerry@hotmail.com"));
    }

}
