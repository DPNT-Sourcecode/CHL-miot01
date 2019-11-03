package befaster.solutions.CHL;

import befaster.solutions.CHL.model.AvailableFreeOffer;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CheckliteSolutionTest {
    private CheckliteSolution solution;

    @Before
    public void setup() {
        solution = new CheckliteSolution();
    }

    @Test
    public void checkQuantityForItemsTest() {
        String input = "ABCA";

        Map<String, Long> quantityPerItems = solution.getQuantityPerItems(input);
        assertThat(quantityPerItems.size(), equalTo(3));

        Long quantityForA = quantityPerItems.get("A");
        assertNotNull(quantityForA);
        assertThat(quantityForA, equalTo(2L));

        Long quantityForB = quantityPerItems.get("B");
        assertNotNull(quantityForB);
        assertThat(quantityForB, equalTo(1L));

        Long quantityForC = quantityPerItems.get("C");
        assertNotNull(quantityForC);
        assertThat(quantityForC, equalTo(1L));
    }

    @Test
    public void checkQuantityEmptyInput() {
        String input1 = null;
        Map<String, Long> quantityPerItem = solution.getQuantityPerItems(input1);
        assertTrue(quantityPerItem.isEmpty());

        String input2 = "";
        Map<String, Long> quantityPerItem2 = solution.getQuantityPerItems(input2);
        assertTrue(quantityPerItem2.isEmpty());

        String input3 = " ";
        Map<String, Long> quantityPerItem3 = solution.getQuantityPerItems(input3);
        assertTrue(quantityPerItem3.isEmpty());
    }

    @Test
    public void checkQuantityTrailingComma() {
        String input = "A";
        Map<String, Long> quantityPerItem = solution.getQuantityPerItems(input);
        assertThat(quantityPerItem.size(), equalTo(1));
    }

    @Test
    public void testBasketPrice() {
        String input = "ABC";
        Integer price = solution.checklite(input);

        assertThat(price, equalTo(100));
    }

    @Test
    public void testBasketPriceWithOffers() {
        String input = "ABABAC";
        Integer price = solution.checklite(input);

        assertThat(price, equalTo(195));
    }

    @Test
    public void testInvalidInput() {
        String input1 = "ABCa";
        Integer price1 = solution.checklite(input1);
        assertThat(price1, equalTo(-1));

        String input2 = "-";
        Integer price2 = solution.checklite(input2);
        assertThat(price2, equalTo(-1));

        String input3 = "a";
        Integer price3 = solution.checklite(input3);
        assertThat(price3, equalTo(-1));
    }

    @Test
    public void testSolution() {
        String input = "ABCDABCD";

        Integer price = solution.checklite(input);
        assertThat(price, equalTo(215));
    }

    @Test
    public void withFreeItems() {
        String input = "EEB";

        Integer price = solution.checklite(input);
        assertThat(price, equalTo(80));
    }

    @Test
    public void testNumberOfFreeItems() {
        String input = "EEEE";
        Map<String, Long> basket = solution.getQuantityPerItems(input);
        Map<String, AvailableFreeOffer> freeItems = solution.getFreeItemsForBasket(basket);

        assertThat(freeItems.get("B").getQuantity(), equalTo(2L));
    }

    @Test
    public void testMultipleLowerOffers() {
        String input = "AAAA";

        Integer price = solution.checklite(input);
        assertThat(price, equalTo(180));

        String input2 = "AAAAAAAA";
        Integer price2 = solution.checklite(input2);
        assertThat(price2, equalTo(330));
    }

    @Test
    public void testBuy2GetOneFree() {
        String input = "FFF";
        Integer price = solution.checklite(input);
        assertThat(price, equalTo(20));

        String input2 = "FF";
        Integer price2 = solution.checklite(input2);
        assertThat(price2, equalTo(20));


        String input3 = "FFFF";
        Integer price3 = solution.checklite(input3);
        assertThat(price3, equalTo(30));
    }
}
