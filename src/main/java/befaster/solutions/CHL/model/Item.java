package befaster.solutions.CHL.model;

import java.util.Map;


public class Item {

    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers;

    private Long highestOfferQuantity = null;

    public Item(
        String sku,
        Integer defaultPrice,
        Map<Long, Integer> offers
    ) {
        this.sku = sku;
        this.defaultPrice = defaultPrice;
        this.specialOffers = offers;
    }

    public Integer getPrice(Long quantity) {
        // we need to check how many times do we have the offer in the quantity we get
        // so we can have an offer for 3 but the quantity is 7 so we would have 2*offer + normal price
        if (quantity > getHighestOfferQuantity()) {
            Long offerNumber = quantity%getHighestOfferQuantity();
            Integer price = offerNumber*specialOffers.get(getHighestOfferQuantity());
        }
        else {
            return specialOffers.getOrDefault(quantity, defaultPrice);
        }
    }

    private Long getHighestOfferQuantity() {
        if (highestOfferQuantity == null) {
            highestOfferQuantity = specialOffers.keySet().stream().max(Long::compareTo).orElse(1L);
        }
        return highestOfferQuantity;
    }
}
