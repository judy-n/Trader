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


    @Test
    public void testGetTimesLentOrBorrowed(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        tm.addTrade(trade);
        trade.closeTransaction();
        assertEquals(1, tm.getTimesLent("Tom"));
        assertEquals(1, tm.getTimesBorrowed("Jerry"));
    }

    @Test
    public void testCancelUnconfirmed(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");
        tm.addTrade(trade);
        trade.confirmAgreedMeeting1();
        trade.confirmTransaction1("Tom");
        tm.cancelAllUnconfirmedTrades();
        assertTrue(trade.getIsCancelled());
    }

    @Test
    public void test1RecentTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade1 = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");
        tm.addTrade(trade1);
        trade1.closeTransaction();
        assertEquals(trade1, tm.getRecentThreeTrades("Tom")[0]);
    }

    @Test
    public void test2RecentTrades(){
        Item item = new Item("book", "A book", "Tom");
        Item item2 = new Item("pencil", "A pencil", "Jerry");
        User1.addInventory(item.getID());
        User2.addInventory(item2.getID());
        PermanentTrade trade2 = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item2.getID()},
                LocalDateTime.of(2020, 7, 6, 11, 0),"UofT");
        PermanentTrade trade1 = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");
        tm.addTrade(trade2);
        tm.addTrade(trade1);
        trade2.closeTransaction();
        trade1.closeTransaction();
        assertEquals(trade2, tm.getRecentThreeTrades("Tom")[1]);
        assertEquals(trade1, tm.getRecentThreeTrades("Tom")[0]);
    }

    @Test
    public void test3RecentTrades(){
        Item item = new Item("book", "A book", "Tom");
        Item item2 = new Item("pencil", "A pencil", "Jerry");
        User1.addInventory(item.getID());
        User2.addInventory(item2.getID());
        PermanentTrade trade2 = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item2.getID()},
                LocalDateTime.of(2020, 7, 6, 11, 0),"UofT");
        PermanentTrade trade1 = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");
        TemporaryTrade trade3 = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item.getID()},
                LocalDateTime.of(2020, 7, 11, 13, 0), "Robarts");
        tm.addTrade(trade2);
        tm.addTrade(trade1);
        tm.addTrade(trade3);
        trade2.closeTransaction();
        trade1.closeTransaction();
        trade3.closeTransaction();
        assertEquals(trade1, tm.getRecentThreeTrades("Tom")[1]);
        assertEquals(trade3, tm.getRecentThreeTrades("Tom")[0]);
        assertEquals(trade2, tm.getRecentThreeTrades("Tom")[2]);
    }



}
