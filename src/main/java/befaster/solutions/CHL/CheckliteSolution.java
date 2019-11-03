package befaster.solutions.CHL;

import befaster.solutions.CHL.model.Item;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckliteSolution {

    private Map<String, Item> marketDatabase = new HashMap<>();

    public CheckliteSolution() {
        Item itemA = new Item()
    }

    public CheckliteSolution(List<Item> items) {

    }

    public Integer checklite(String skus) {
        Map<String, Long> basket = getQuantityPerItems(skus);

        basket.keySet().stream()
            .map(key -> {
                Long quantity = basket.get(key);

            });
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



