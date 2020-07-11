import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ItemManagerTest {

    NormalUser User1 = new NormalUser("Ackle", "ackle@gmail.com", "gol");
    ItemManager im = new ItemManager();
    UserManager um = new UserManager();
    TradeManager tm = new TradeManager();

    @Test
    public void testAddToPendingInventory(){
        Item requestedItem = new Item("book", "A book", "Ackle");
        User1.addPendingInventory(requestedItem.getID());
        im.addPendingItem(requestedItem);
        assert User1.getPendingInventory().contains(requestedItem.getID());
        assert im.getPendingItems().contains(requestedItem);
    }

    @Test
    public void testApproveItems(){
        Item requestedItem = new Item("book", "A book", "Ackle");
        User1.addPendingInventory(requestedItem.getID());
        im.addPendingItem(requestedItem);
        im.approveItem(requestedItem);
        assert im.getPendingItems().isEmpty();
        assert im.getApprovedItems().contains(requestedItem);
        User1.addInventory(requestedItem.getID());
        assert User1.getInventory().contains(requestedItem.getID());
    }

    @Test
    public void testRejectItems(){
        Item requestedItem = new Item("book", "A book", "Ackle");
        User1.addPendingInventory(requestedItem.getID());
        im.addPendingItem(requestedItem);
        im.rejectItem(requestedItem);
        assert im.getPendingItems().isEmpty();
        assert im.getApprovedItems().isEmpty();
        assert User1.getInventory().isEmpty();
    }

    @Test
    public void testRemoveInventory() {
        Item item = new Item("book", "A book", "Ackle");
        User1.addInventory(item.getID());
        User1.removeInventory(item.getID());
        assert User1.getInventory().isEmpty();
    }


}
