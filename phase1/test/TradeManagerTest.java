import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TradeManagerTest {

    TradeManager tm = new TradeManager();
    NormalUser User1 = new NormalUser("Tom", "tom@hotmail.com", "123");
    NormalUser User2 = new NormalUser("Jerry", "jerry@hotmail.com", "123");

    @Test
    public void testAddTemporaryTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        tm.addTrade(trade);
        assert tm.getAllTempTrades().contains(trade);
        assert tm.getAllTrades().contains(trade);
    }
    @Test
    public void testAddPermanentTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        tm.addTrade(trade);
        assert tm.getAllPermTrades().contains(trade);
        assert tm.getAllTrades().contains(trade);
    }

    @Test
    public void testRemoveTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        tm.addTrade(trade);
        tm.removeTrade(trade);
        assert tm.getAllPermTrades().isEmpty();
        assert tm.getAllTrades().isEmpty();
    }

    @Test
    public void testGetOngoingTrades(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        tm.addTrade(trade);
        assert tm.getOngoingTrades("Tom").contains(trade);
        assert tm.getOngoingTrades("Jerry").contains(trade);
    }

    @Test
    public void testGetItemCancelled(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        tm.addTrade(trade);
        assertFalse(tm.getItemInCancelledTrade(item));
        trade.setIsCancelled();
        assertTrue(tm.getItemInCancelledTrade(item));
    }


}
