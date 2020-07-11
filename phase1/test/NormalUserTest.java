import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NormalUserTest {

    NormalUser User1 = new NormalUser("Ackle", "ackle@gmail.com", "gol");


    @Test
    public void testUserPassword(){

        String User1Password = User1.getPassword();
        assertEquals("gol", User1Password);

    }

    @Test
    public void testUserEmail(){

        String User1Email = User1.getEmail();
        assertEquals("ackle@gmail.com", User1Email);

    }

    @Test
    public void testUserUsername(){

        String User1Username = User1.getUsername();
        assertEquals("Ackle", User1Username);

    }

    @Test
    public void testUserFrozen(){
        assertFalse(User1.getIsFrozen());
    }

}
