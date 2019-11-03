package befaster.solutions.CHL;

import befaster.solutions.CHL.model.AvailableFreeOffer;
import befaster.solutions.CHL.model.FreeItemOffer;
import befaster.solutions.CHL.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemTest {

    private Item item;

    @Before
    public void setup() {
        Map<Long, Integer> itemAOffers = new HashMap<>();
        itemAOffers.put(3L, 130);
        itemAOffers.put(5L, 200);
        Map<Long, FreeItemOffer> freeItemOffers = new HashMap<>();
        freeItemOffers.put(2L, new FreeItemOffer("B", 1L, 2L));
        this.item = new Item(
            "A",
            50,
            itemAOffers,
            freeItemOffers
        );
    }

    @Test
    public void testNormalPrice() {
        Integer price = item.getPrice(1L);

        assertThat(price, equalTo(50));
    }

    @Test
    public void testOneOffer() {
        Integer price = item.getPrice(3L);

        assertThat(price, equalTo(130));
    }

    @Test
    public void testMultipleOffersOnly() {
        Integer price = item.getPrice(10L);

        assertThat(price, equalTo(400));
    }

    @Test
    public void testCombinedQuantity() {
        Integer price = item.getPrice(6L);

        assertThat(price, equalTo(250));
    }

    @Test
    public void freeItem() {
        Map<String, AvailableFreeOffer> freeItems = item.getFreeItems(2L);

        assertThat(freeItems.get("B").getQuantity(), equalTo(1L));
    }

    @Test
    public void multipleFreeItem() {
        Map<String, AvailableFreeOffer> freeItems = item.getFreeItems(5L);

        assertThat(freeItems.get("B").getQuantity(), equalTo(2L));
    }

    @Test
    public void noFreeItem() {
        Map<String, AvailableFreeOffer> freeItems = item.getFreeItems(1L);

        assertTrue(freeItems.isEmpty());
    }

    @Test
    public void noFreeItemOffer() {
        Item item2 = new Item(
            "B",
            50,
            Collections.emptyMap(),
            Collections.emptyMap()
        );

        Map<String, AvailableFreeOffer> freeItems = item2.getFreeItems(1L);
        assertTrue(freeItems.isEmpty());
    }

    @Test
    public void testMultipleOffers() {
        Integer price = item.getPrice(8L);
        assertThat(price, equalTo(330));

        Integer price2 = item.getPrice(7L);
        assertThat(price2, equalTo(300));
    }
}

