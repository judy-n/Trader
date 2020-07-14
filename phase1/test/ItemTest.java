import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    Item item = new Item("book", "A book", "John");


    @Test
    public void testItemName(){
        String name = item.getName();
        assertEquals("book", name);
    }

    @Test
    public void testItemDescription(){
        String desc = item.getDescription();
        assertEquals("A book", desc);
    }

    @Test
    public void testItemOwner(){
        String itemOwner = item.getOwnerUsername();
        assertEquals("John", itemOwner);
    }

    @Test
    public void testItemAvailability(){
        // Should be initially available.
        boolean available = item.getAvailability();
        assertTrue(available);
    }

    @Test
    public void testItemToString(){
        String itemString = item.toString();
        assertEquals("book: A book", itemString);
    }
}