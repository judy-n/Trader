import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminUserTest {

    AdminUser Admin = new AdminUser("Ackle", "ackle@gmail.com", "gol", 1);

    @Test
    public void testAdminID(){

        int adminID = Admin.getAdminID();
        assertEquals(1, adminID);

    }

    @Test
    public void testAdminPassword(){

        String adminPassword = Admin.getPassword();
        assertEquals("gol", adminPassword);

    }

    @Test
    public void testAdminEmail(){

        String adminEmail = Admin.getEmail();
        assertEquals("ackle@gmail.com", adminEmail);

    }

    @Test
    public void testAdminUsername(){

        String adminUsername = Admin.getUsername();
        assertEquals("Ackle", adminUsername);

    }

}