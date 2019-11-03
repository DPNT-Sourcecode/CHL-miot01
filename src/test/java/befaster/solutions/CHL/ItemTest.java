package befaster.solutions.CHL;

import befaster.solutions.CHL.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class ItemTest {

    private Item item;

    @Before
    public void setup() {
        Map<Long, Integer> itemAOffers = new HashMap<>();
        itemAOffers.put(3L, 130);
        this.item = new Item(
            "A",
            50,
            itemAOffers
        );
    }


    @Test
    public void testNormalPrice() {
        Integer price = item.getPrice(1L);

        assertThat(price, equalTo(50));
    }

    @Test
    public void testOneOffer() {
        
    }

}

