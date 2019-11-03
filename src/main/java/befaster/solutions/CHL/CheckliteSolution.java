package befaster.solutions.CHL;

import befaster.runner.SolutionNotImplementedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckliteSolution {
    public Integer checklite(String skus) {
        Map<String, Long> basket = getQuantityPerItems(skus);

        
    }

    /**
     * Gets the quantity for each item in the basket
     * @param skus comma separated list of items
     * @return map of item to quantity
     */
    public Map<String, Long> getQuantityPerItems(String skus) {
        if (skus == null || skus.isEmpty()) {
            return Collections.emptyMap();
        }
        // removing empty space so we don't miss indentify items
        skus = skus.replaceAll("\\s+","");

        // we assume that the sku is a comma separate list of items
        List<String> items = Arrays.asList(skus.split(","));

        return items.stream()
            .filter(string -> ! string.isEmpty())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}






