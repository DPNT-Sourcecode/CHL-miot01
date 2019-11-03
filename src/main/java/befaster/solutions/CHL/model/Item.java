package befaster.solutions.CHL.model;

import java.util.Map;


public class Item {

    private String sku;
    // the price for one item
    private Long defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Long> specialOffers;

    public Item(
        String sku,
        Long defaultPrice,
        Map<Long, Long> offers
    ) {
        this.sku = sku;
        this.defaultPrice = defaultPrice;
        this.specialOffers = offers;
    }

    public Long getPrice(Long quantity) {
        return specialOffers.getOrDefault(quantity, defaultPrice);
    }
}



