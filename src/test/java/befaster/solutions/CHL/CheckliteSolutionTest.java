package befaster.solutions.CHL;

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
        String input = "A,B,C,A";

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
        String input = "A,";
        Map<String, Long> quantityPerItem = solution.getQuantityPerItems(input);
        assertThat(quantityPerItem.size(), equalTo(1));
    }

    @Test
    public void testBasketPrice() {
        String input = "A,B,C";
        Integer price = solution.checklite(input);

        assertThat(price, equalTo(100));
    }

    @Test
    public void testBasketPriceWithOffers() {
        String input = "A,B,A,B,A,C";
        Integer price = solution.checklite(input);

        assertThat(price, equalTo(195));
    }
}

