package befaster.solutions.CHL.model;

import java.util.Map;


public class Item {

    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers;

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
        return specialOffers.getOrDefault(quantity, defaultPrice);
    }
}




