import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountFreezerTest {
    NormalUser user1 = new NormalUser("fake", "fake@gmail.com", "person");

    @Test
    public void testUserActive(){
        assertEquals(false, user1.getIsFrozen());
    }



}
