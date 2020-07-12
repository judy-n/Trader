import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {

    NormalUser User1 = new NormalUser("Tom", "tom@hotmail.com", "123");
    NormalUser User2 = new NormalUser("Jerry", "jerry@hotmail.com", "123");

    @Test
    public void TradeStatusTest(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertFalse(trade.getIsComplete());
        assertFalse(trade.getIsCancelled());
        assertFalse(trade.getHasAgreedMeeting1());
    }

    @Test
    public void TradeGetMeeting1Test(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals(LocalDateTime.of(2020, 8, 12, 5, 0),
                trade.getMeetingDateTime1());
        assertEquals("Robarts", trade.getMeetingLocation1());
    }

    @Test
    public void TradeInvolvedUsernamesTest(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals("Tom",trade.getInvolvedUsernames()[0]);
        assertEquals("Jerry",trade.getInvolvedUsernames()[1]);
    }


    @Test
    public void TradeInvolveItemsTest(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals(item.getID(),trade.getInvolvedItemIDs()[0]);
        assertEquals(0,trade.getInvolvedItemIDs()[1]);
    }


    @Test
    public void TradeLentItemTest(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals(item.getID(), trade.getLentItemID("Tom"));
    }

    @Test
    public void TradeIsInvolvedTest(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertTrue(trade.isInvolved("Jerry"));
        assertTrue(trade.isInvolved("Tom"));
        assertFalse(trade.isInvolved("Meow"));
    }

    @Test
    public void TradeGetOtherUsernameTest(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals("Jerry", trade.getOtherUsername("Tom"));
        assertEquals("Tom", trade.getOtherUsername("Jerry"));
    }

    @Test
    public void TemporaryTradeToString(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        TemporaryTrade trade = new TemporaryTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals("Temporary trade with < Jerry > - ", trade.toString("Tom"));
        assertEquals("Temporary trade with < Tom > - ", trade.toString("Jerry"));
    }

    @Test
    public void PermanentTradeToString(){
        Item item = new Item("book", "A book", "Tom");
        User1.addInventory(item.getID());
        PermanentTrade trade = new PermanentTrade(new String[]{"Tom", "Jerry"}, new long[]{item.getID(), 0},
                LocalDateTime.of(2020, 8, 12, 5, 0), "Robarts");
        assertEquals("Permanent trade with < Jerry > - ", trade.toString("Tom"));
        assertEquals("Permanent trade with < Tom > - ", trade.toString("Jerry"));
    }





}
