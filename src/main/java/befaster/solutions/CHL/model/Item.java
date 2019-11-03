package befaster.solutions.CHL.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class Item {

    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers;

    // map of free item offers, quantity -item sku
    private Map<Long, String> freeItemOffers;
    private Long highestFreeItemQuantity;

    public Item(
        String sku,
        Integer defaultPrice,
        Map<Long, Integer> offers,
        Map<Long, String> freeItemOffers
    ) {
        this.sku = sku;
        this.defaultPrice = defaultPrice;
        this.specialOffers = offers;
        this.freeItemOffers = freeItemOffers;
    }

    public Integer getPrice(Long quantity) {
        Long remainingQuantity = quantity;
        Integer price = 0;
        while (remainingQuantity > 0) {
            // get highest offer
            Long highestOfferQuantity = getHighestQuantityOffer(specialOffers.keySet(), remainingQuantity);
            if (highestOfferQuantity == 0) {
                break;
            }
            remainingQuantity -= highestOfferQuantity;
            price += specialOffers.get(highestOfferQuantity);
        }

        if (remainingQuantity > 0) {
            price += remainingQuantity.intValue() * defaultPrice;
        }
        return price;
    }

    private Long getHighestQuantityOffer(Set<Long> quantityForOffer, Long quantity) {
        return quantityForOffer
            .stream()
            .filter(offerQuantity -> offerQuantity <= quantity)
            .max(Long::compareTo)
            .orElse(0L);
    }

    public Map<String, Long> getFreeItems(Long quantity) {
        Map<String, Long> freeItems = new HashMap<>();

        if (! freeItemOffers.isEmpty() && quantity > getHighestFreeItemQuantity()) {
            Long offerNumber = quantity/getHighestFreeItemQuantity();
            freeItems.put(freeItemOffers.get(getHighestFreeItemQuantity()), offerNumber);
        }
        else {
            String itemSku = freeItemOffers.get(quantity);
            if (itemSku != null) {
                freeItems.put(itemSku, 1L);
            }
        }

        return freeItems;
    }

    private Long getNumberOfFreeItems(Long quantity) {
        Long remainingQuantity = quantity;
        Integer number = 0;
        while (remainingQuantity > 0) {
            // get highest offer
            Long highestOfferQuantity = getHighestQuantityOffer(freeItemOffers.keySet(), remainingQuantity);
            if (highestOfferQuantity == 0) {
                break;
            }
            remainingQuantity -= highestOfferQuantity;
            price += specialOffers.get(highestOfferQuantity);
        }
    }

    private Long getHighestFreeItemQuantity() {
        if (highestFreeItemQuantity == null) {
            highestFreeItemQuantity = freeItemOffers.keySet().stream().max(Long::compareTo).orElse(1L);
        }
        return highestFreeItemQuantity;
    }

}




