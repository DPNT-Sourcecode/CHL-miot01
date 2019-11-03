package befaster.solutions.CHL;

import org.hamcrest.Matchers;
import org.junit.Assert;
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


}


