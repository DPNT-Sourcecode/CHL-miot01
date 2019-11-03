package befaster.solutions.CHL.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class Item {

    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers;
    private Long highestOfferQuantity;

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
        // we need to check how many times do we have the offer in the quantity we get
        // so we can have an offer for 3 but the quantity is 7 so we would have 2*offer + normal price
        if (! specialOffers.isEmpty() && quantity > getHighestOfferQuantity()) {
            Long offerNumber = quantity/getHighestOfferQuantity();
            Long remainingSoloItems = quantity%getHighestOfferQuantity();
            return
                offerNumber.intValue()*specialOffers.get(getHighestOfferQuantity())
                + remainingSoloItems.intValue() * defaultPrice;

        }
        else {
            return Optional
                .ofNullable(specialOffers.get(quantity))
                .orElse(quantity.intValue() * defaultPrice);
        }
    }

    public Integer getPrice2(Long quantity) {
        Long remainingQuantity = quantity;
        Integer price = 0;
        while (remainingQuantity > 0) {
            // get highest offer
            Long highestOfferQuantity = getHighestQuantityOffer(remainingQuantity);
            remainingQuantity -= highestOfferQuantity;
            price += specialOffers.get(highestOfferQuantity);
        }
    }

    private Long getHighestQuantityOffer(Long quantity) {
        return specialOffers.keySet()
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

    private Long getHighestFreeItemQuantity() {
        if (highestFreeItemQuantity == null) {
            highestFreeItemQuantity = freeItemOffers.keySet().stream().max(Long::compareTo).orElse(1L);
        }
        return highestFreeItemQuantity;
    }

    private Long getHighestOfferQuantity() {
        if (highestOfferQuantity == null) {
            highestOfferQuantity = specialOffers.keySet().stream().max(Long::compareTo).orElse(1L);
        }
        return highestOfferQuantity;
    }
}

