package befaster.solutions.CHL.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Item {

    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers;

    // map of free item offers, quantity -item sku
    private Map<Long, FreeItemOffer> freeItemOffers;
    private Long highestFreeItemQuantity;

    public Item(
        String sku,
        Integer defaultPrice,
        Map<Long, Integer> offers,
        Map<Long, FreeItemOffer> freeItemOffers
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

    /**
     * Get the free items offered by this item for the given quantity
     * @param quantity to lookup
     * @return map of item to available offer
     */
    public Map<String, AvailableFreeOffer> getFreeItems(Long quantity) {
        Map<String, AvailableFreeOffer> freeItems = new HashMap<>();

        if (! freeItemOffers.isEmpty() && quantity > getHighestFreeItemQuantity()) {
            Long offerNumber = quantity/getHighestFreeItemQuantity();
            FreeItemOffer offer = freeItemOffers.get(getHighestFreeItemQuantity());

            freeItems.put(
                offer.getOfferedItem(),
                new AvailableFreeOffer(offer, offerNumber));
        }
        else {
            FreeItemOffer offer = freeItemOffers.get(quantity);
            if (offer != null) {
                freeItems.put(offer.getOfferedItem(), new AvailableFreeOffer(offer, 1L));
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

}
