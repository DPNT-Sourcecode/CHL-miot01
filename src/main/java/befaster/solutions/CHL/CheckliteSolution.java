package befaster.solutions.CHL;

import befaster.solutions.CHL.model.Item;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckliteSolution {

    private Map<String, Item> marketDatabase = new HashMap<>();

    public CheckliteSolution() {
        // Hardcoding this to save time
        Map<Long, Integer> itemAOffers = new HashMap<>();
        itemAOffers.put(3L, 130);
        itemAOffers.put(5L, 200);
        Item itemA = new Item(
            "A",
            50,
            itemAOffers,
            Collections.emptyMap()
        );
        marketDatabase.put("A", itemA);

        Map<Long, Integer> itemBOffers = new HashMap<>();
        itemBOffers.put(2L, 45);
        Item itemB = new Item(
            "B",
            30,
            itemBOffers,
            Collections.emptyMap()
        );
        marketDatabase.put("B", itemB);

        Item itemC = new Item(
            "C",
            20,
            Collections.emptyMap(),
            Collections.emptyMap()
        );
        marketDatabase.put("C", itemC);

        Item itemD = new Item(
            "D",
            15,
            Collections.emptyMap(),
            Collections.emptyMap()
        );
        marketDatabase.put("D", itemD);

        Map<Long, String> freeItemsE = new HashMap<>();
        freeItemsE.put(2L, "B");
        Item itemE = new Item(
            "E",
            40,
            Collections.emptyMap(),
            freeItemsE
        );
        marketDatabase.put("E", itemE);
    }

    public Integer checklite(String skus) {
        if (! isInputValid(skus)) {
            return -1;
        }

        Map<String, Long> basket = getQuantityPerItems(skus);
        if (basket.isEmpty()) {
            return 0;
        }

        // remove free items
        final Map<String, Long> freeItems = getFreeItemsForBasket(basket);
        basket.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    Long freeItemCount = freeItems.getOrDefault(entry.getKey(), 0L);
                    Long remaining = entry - 
                }
            ))

        return basket.keySet().stream()
            .mapToInt(key -> {
                Long quantity = basket.get(key);
                Item currentItem = marketDatabase.get(key);
                if (currentItem == null) {
                    return 0;
                }
                else {
                    Integer price = currentItem.getPrice(quantity);
                    return price;
                }
            })
            .sum();
    }

    private Map<String, Long> getFreeItemsForBasket(Map<String, Long> basket) {
        return basket.keySet().stream()
            .map(key -> {
                Item currentItem = marketDatabase.get(key);
                if (currentItem != null) {
                    return currentItem.getFreeItems(basket.get(key));
                }
                else {
                    return Collections.<String, Long>emptyMap();
                }
            })
            .map(Map::entrySet)
            .flatMap(Collection::stream)
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    Long::sum
                )
            );
    }

    private boolean isInputValid(String skus) {
        char[] chars = skus.toCharArray();
        for (char c: chars) {
            if (! Character.isUpperCase(c)) {
                return false;
            }
        }

        return true;
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
        List<String> items = skus.chars()
            .mapToObj(c -> (char) c)
            .map(character -> String.valueOf(character))
            .collect(Collectors.toList());

        return items.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}






