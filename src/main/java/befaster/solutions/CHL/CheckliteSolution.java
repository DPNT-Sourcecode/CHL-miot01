package befaster.solutions.CHL;

import befaster.solutions.CHL.model.Item;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckliteSolution {

    private Map<String, Item> marketDatabase = new HashMap<>();

    public CheckliteSolution() {
        // Hardcoding this to save time
        Map<Long, Long> itemAOffers = new HashMap<>();
        itemAOffers.put(3L, 130L);
        Item itemA = new Item(
            "A",
            50L,
            itemAOffers
        );
        marketDatabase.put("A", itemA);

        Map<Long, Long> itemBOffers = new HashMap<>();
        itemAOffers.put(2L, 45L);
        Item itemB = new Item(
            "B",
            30L,
            itemAOffers
        );
        marketDatabase.put("B", itemB);

        Item itemC = new Item(
            "C",
            20L,
            Collections.emptyMap()
        );
        marketDatabase.put("C", itemB);

        Item itemD = new Item(
            "D",
            15L,
            Collections.emptyMap()
        );
        marketDatabase.put("D", itemD);
    }

    public Integer checklite(String skus) {
        Map<String, Long> basket = getQuantityPerItems(skus);

        basket.keySet().stream()
            .map(key -> {
                Long quantity = basket.get(key);
                Item currentItem = marketDatabase.get(key);
                if (currentItem == null) {
                    return 0;
                }
                else {
                    return currentItem.getPrice(quantity);
                }
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





