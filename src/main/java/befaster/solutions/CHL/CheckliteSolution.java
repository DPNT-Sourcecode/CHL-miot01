package befaster.solutions.CHL;

import befaster.solutions.CHL.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckliteSolution {

    private Map<String, Item> marketDatabase = new HashMap<>();

    public CheckliteSolution() {
        // Hardcoding this to save time
        Item itemA = new ItemBuilder()
            .sku("A")
            .price(50)
            .addSpecialOffer(3L, 130)
            .addSpecialOffer(5L, 200)
            .build();
        marketDatabase.put("A", itemA);

        Item itemB = new ItemBuilder()
            .sku("B")
            .price(30)
            .addSpecialOffer(2L, 45)
            .build();
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

        Item itemE = new ItemBuilder()
            .sku("E")
            .price(40)
            .addFreeItemOffer(
                2L,
                new FreeItemOfferBuilder()
                    .item("B")
                    .quantity(1L)
                    .build()
            )
            .build();
        marketDatabase.put("E", itemE);

        Item itemF = new ItemBuilder()
            .sku("F")
            .price(10)
            .addFreeItemOffer(
                2L,
                new FreeItemOfferBuilder()
                    .item("F")
                    .quantity(1L)
                    .requiredQuantity(3L)
                    .build()
            )
            .build();
        marketDatabase.put("F", itemF);

        Item itemG = new ItemBuilder()
            .sku("G")
            .price(20)
            .build();
        marketDatabase.put("G", itemG);

        Item itemH = new ItemBuilder()
            .sku("H")
            .price(10)
            .addSpecialOffer(5L, 45)
            .addSpecialOffer(10L, 80)
            .build();
        marketDatabase.put("H", itemH);

        Item itemI = new ItemBuilder()
            .sku("I")
            .price(35)
            .build();
        marketDatabase.put("I", itemI);

        Item itemJ = new ItemBuilder()
            .sku("J")
            .price(60)
            .build();
        marketDatabase.put("J", itemJ);

        Item itemK = new ItemBuilder()
            .sku("K")
            .price(80)
            .addSpecialOffer(2L, 150)
            .build();
        marketDatabase.put("K", itemK);

        Item itemL = new ItemBuilder()
            .sku("L")
            .price(90)
            .build();
        marketDatabase.put("L", itemL);

        Item itemM = new ItemBuilder()
            .sku("M")
            .price(15)
            .build();
        marketDatabase.put("M", itemM);

        Item itemN = new ItemBuilder()
            .sku("N")
            .price(40)
            .addFreeItemOffer(3L, new FreeItemOfferBuilder()
                .quantity(1L)
                .item("M")
                .build()
            )
            .build();
        marketDatabase.put("N", itemN);

        Item itemO = new ItemBuilder()
            .sku("O")
            .price(10)
            .build();
        marketDatabase.put("O", itemO);

        Item itemP = new ItemBuilder()
            .sku("P")
            .price(50)
            .addSpecialOffer(5L, 200)
            .build();
        marketDatabase.put("P", itemP);

        Item itemQ = new ItemBuilder()
            .sku("Q")
            .price(30)
            .addSpecialOffer(3L, 80)
            .build();
        marketDatabase.put("Q", itemQ);

        Item itemR = new ItemBuilder()
            .sku("R")
            .price(50)
            .addFreeItemOffer(3L, new FreeItemOfferBuilder()
                .quantity(1L)
                .item("Q")
                .build()
            )
            .build();
        marketDatabase.put("R", itemR);

        Item itemS = new ItemBuilder()
            .sku("S")
            .price(30)
            .build();
        marketDatabase.put("S", itemS);

        Item itemT = new ItemBuilder()
            .sku("T")
            .price(20)
            .build();
        marketDatabase.put("T", itemT);

        Item itemU = new ItemBuilder()
            .sku("U")
            .price(40)
            .addFreeItemOffer(3L, new FreeItemOfferBuilder()
                .quantity(1L)
                .requiredQuantity(3L)
                .item("U")
                .build()
            )
            .build();
        marketDatabase.put("U", itemU);
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
                    if (freeOffer != null) {
                        return entry.getValue() - freeOffer.getValidQuantity(entry.getValue());
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
                    (availableFreeOffer, availableFreeOffer2) -> new AvailableFreeOffer(
                        availableFreeOffer.getOffer(),
                        availableFreeOffer.getQuantity() + availableFreeOffer2.getQuantity()
                    )
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





