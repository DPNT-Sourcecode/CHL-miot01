package befaster.solutions.CHL.model;

import java.util.Map;

public class ItemBuilder {
    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers;

    // map of free item offers, quantity -item sku
    private Map<Long, FreeItemOffer> freeItemOffers;
}
