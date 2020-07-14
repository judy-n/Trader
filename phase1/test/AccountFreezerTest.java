import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountFreezerTest {
    NormalUser user1 = new NormalUser("fake", "fake@gmail.com", "person");

    @Test
    public void testUserActive(){
        assertEquals(false, user1.getIsFrozen());
    }

    @Test
    public void testUserFrozen(){
        user1.freeze();
        assertEquals(true, user1.getIsFrozen());
    }

    // testing if active again after freezing above^
    @Test
    public void testUserReActive(){
        user1.unfreeze();
        assertEquals(false, user1.getIsFrozen());
    }

    @Test
    public void testUserReFrozen(){
        user1.freeze();
        assertEquals(true, user1.getIsFrozen());
    }
}
