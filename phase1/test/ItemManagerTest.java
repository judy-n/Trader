import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ItemManagerTest {

    NormalUser User1 = new NormalUser("Ackle", "ackle@gmail.com", "gol");
    ItemManager im = new ItemManager();

    @Test
    public void testAddToPendingInventory(){
        long newItemID = im.createItem("book", "A book", "Ackle");
        User1.addPendingInventory(newItemID);
        assert User1.getPendingInventory().contains(newItemID);
        assert im.getPendingItems().contains(im.getPendingItem(newItemID));
    }

    @Test
    public void testApproveItems(){
        long newItemID = im.createItem("book", "A book", "Ackle");
        User1.addPendingInventory(newItemID);

        Item newItem = im.getPendingItem(newItemID);
        im.approveItem(newItem);
        assert im.getPendingItems().isEmpty();
        assert im.getApprovedItems().contains(newItem);
        User1.addInventory(newItemID);
        assert User1.getInventory().contains(newItemID);
    }

    @Test
    public void testRejectItems(){
        long newItemID = im.createItem("book", "A book", "Ackle");
        User1.addPendingInventory(newItemID);

        Item newItem = im.getPendingItem(newItemID);
        im.rejectItem(newItem);
        assert im.getPendingItems().isEmpty();
        assert im.getApprovedItems().isEmpty();
        assert User1.getInventory().isEmpty();
    }

    @Test
    public void testRemoveInventory() {
        long newItemID = im.createItem("book", "A book", "Ackle");
        User1.addPendingInventory(newItemID);

        User1.addInventory(newItemID);
        User1.removeInventory(newItemID);
        assert User1.getInventory().isEmpty();
    }


}
