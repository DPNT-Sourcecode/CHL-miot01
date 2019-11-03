package befaster.solutions.CHL;

import befaster.solutions.CHL.model.AvailableFreeOffer;
import befaster.solutions.CHL.model.FreeItemOffer;
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

        Map<Long, FreeItemOffer> freeItemsE = new HashMap<>();
        freeItemsE.put(2L, new FreeItemOffer("B", 1L, 2L));
        Item itemE = new Item(
            "E",
            40,
            Collections.emptyMap(),
            freeItemsE
        );
        marketDatabase.put("E", itemE);

        Map<Long, FreeItemOffer> freeItemsF = new HashMap<>();
        freeItemsF.put(2L, new FreeItemOffer("F", 1L, 3L));
        Item itemF = new Item(
            "F",
            10,
            Collections.emptyMap(),
            freeItemsF
        );
        marketDatabase.put("F", itemF);
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
        final Map<String, AvailableFreeOffer> freeItems = getFreeItemsForBasket(basket);
        Map<String, Long> finalBasket = basket.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    AvailableFreeOffer freeOffer = freeItems.getOrDefault(entry.getKey(), null);
                    if (freeOffer != null && freeOffer.getOffer().getRequiredQuantity() < basket.get(entry.getKey())) {
                        return entry.getValue() - freeOffer.getQuantity();
                    }
                    else {
                        return entry.getValue();
                    }
                }
            ))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() > 0L)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
                )
            );

        return finalBasket.keySet().stream()
            .mapToInt(key -> {
                Long quantity = finalBasket.get(key);
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

    // Is public only for testing.. doing this for speed
    public Map<String, AvailableFreeOffer> getFreeItemsForBasket(Map<String, Long> basket) {
        return basket.keySet().stream()
            .map(key -> {
                Item currentItem = marketDatabase.get(key);
                if (currentItem != null) {
                    return currentItem.getFreeItems(basket.get(key));
                }
                else {
                    return Collections.<String, AvailableFreeOffer>emptyMap();
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






