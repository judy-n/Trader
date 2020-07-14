import org.junit.Test;

import java.time.LocalDate;
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
        TemporaryTrade trade = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        assert tm.getAllTempTrades().contains(trade);
        assert tm.getAllTrades().contains(trade);
    }
    @Test
    public void testAddPermanentTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        assert tm.getAllPermTrades().contains(trade);
        assert tm.getAllTrades().contains(trade);
    }

    @Test
    public void testRemoveTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        tm.removeTrade(trade);
        assert tm.getAllPermTrades().isEmpty();
        assert tm.getAllTrades().isEmpty();
    }

    @Test
    public void testGetOngoingTrades(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        assert tm.getOngoingTrades("Tom").contains(trade);
        assert tm.getOngoingTrades("Jerry").contains(trade);
    }

    @Test
    public void testGetItemCancelled(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        assertFalse(tm.getItemInCancelledTrade(item));
        trade.setIsCancelled();
        assertTrue(tm.getItemInCancelledTrade(item));
    }


    @Test
    public void testGetTimesLentOrBorrowed(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        trade.closeTransaction();
        assertEquals(1, tm.getTimesLent("Tom"));
        assertEquals(1, tm.getTimesBorrowed("Jerry"));
    }

    @Test
    public void testCancelUnconfirmed(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");

        trade.confirmAgreedMeeting1();
        trade.confirmTransaction1("Tom");
        tm.cancelAllUnconfirmedTrades();
        assertTrue(trade.getIsCancelled());
    }

    @Test
    public void test1RecentTrade(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade1 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");

        trade1.closeTransaction();
        assertEquals(trade1, tm.getRecentThreeTrades("Tom")[0]);
    }

    @Test
    public void test2RecentTrades(){
        Item item = new Item("book", "A book", "Tom");
        Item item2 = new Item("pencil", "A pencil", "Jerry");
        User1.addInventory(item.getID());
        User2.addInventory(item2.getID());
        PermanentTrade trade2 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item2.getID()},
                LocalDateTime.of(2020, 7, 6, 11, 0),"UofT");
        PermanentTrade trade1 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");

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
        PermanentTrade trade2 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item2.getID()},
                LocalDateTime.of(2020, 7, 6, 11, 0),"UofT");
        PermanentTrade trade1 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");
        TemporaryTrade trade3 = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item.getID()},
                LocalDateTime.of(2020, 7, 11, 13, 0), "Robarts");

        trade2.closeTransaction();
        trade1.closeTransaction();
        trade3.closeTransaction();
        assertEquals(trade1, tm.getRecentThreeTrades("Tom")[1]);
        assertEquals(trade3, tm.getRecentThreeTrades("Tom")[0]);
        assertEquals(trade2, tm.getRecentThreeTrades("Tom")[2]);
    }

    @Test
    public void testFrequentTradersNoTrades() {
        assertEquals("empty", tm.getFrequentTradePartners("Tom")[0]);
        assertEquals("empty", tm.getFrequentTradePartners("Tom")[1]);
        assertEquals("empty", tm.getFrequentTradePartners("Tom")[2]);
    }

    @Test
    public void testFrequentTraders() {
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");

        trade.closeTransaction();
        assertEquals("Jerry", tm.getFrequentTradePartners("Tom")[0]);
        assertEquals("empty", tm.getFrequentTradePartners("Tom")[1]);
        assertEquals("empty", tm.getFrequentTradePartners("Tom")[2]);
        assertEquals("Tom", tm.getFrequentTradePartners("Jerry")[0]);
    }

    @Test
    public void testGetMeetingsThisWeek(){
        Item item = new Item("book", "A book", "Tom");
        Item item2 = new Item("pencil", "A pencil", "Jerry");
        User1.addInventory(item.getID());
        User2.addInventory(item2.getID());
        PermanentTrade trade2 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item2.getID()},
                LocalDateTime.of(2020, 7, 8, 11, 0),"UofT");
        PermanentTrade trade1 = tm.createPermTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 7, 10, 13, 0), "Robarts");
        TemporaryTrade trade3 = tm.createTempTrade(new String[]{"Tom", "Jerry"}, new long[]{0, item.getID()},
                LocalDateTime.of(2020, 7, 12, 13, 0), "Robarts");

        trade1.confirmAgreedMeeting1();
        trade2.confirmAgreedMeeting1();
        trade3.confirmAgreedMeeting1();
        assertEquals(3, tm.getNumMeetingsThisWeek("Tom", LocalDate.of(2020, 7, 8)));
    }





}
