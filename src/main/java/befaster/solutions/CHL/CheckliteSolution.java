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
        Item itemA = new Item(
            "A",
            50,
            itemAOffers
        );
        marketDatabase.put("A", itemA);

        Map<Long, Integer> itemBOffers = new HashMap<>();
        itemBOffers.put(2L, 45);
        Item itemB = new Item(
            "B",
            30,
            itemBOffers
        );
        marketDatabase.put("B", itemB);

        Item itemC = new Item(
            "C",
            20,
            Collections.emptyMap()
        );
        marketDatabase.put("C", itemC);

        Item itemD = new Item(
            "D",
            15,
            Collections.emptyMap()
        );
        marketDatabase.put("D", itemD);
    }

    public Integer checklite(String skus) {
        if (! isInputValid(skus)) {
            return -1;
        }

        Map<String, Long> basket = getQuantityPerItems(skus);
        if (basket.isEmpty()) {
            return 0;
        }

        return basket.keySet().stream()
            .mapToInt(key -> {
                Long quantity = basket.get(key);
                Item currentItem = marketDatabase.get(key);
                if (currentItem == null) {
                    System.out.println(key + " " + 0);
                    return 0;
                }
                else {
                    Integer price = currentItem.getPrice(quantity);
                    System.out.println(key + " " + price);
                    return price;
                }
            })
            .sum();
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




