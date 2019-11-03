package befaster.solutions.CHL.model;

import java.util.HashMap;
import java.util.Map;

public class ItemBuilder {
    private String sku;
    // the price for one item
    private Integer defaultPrice;
    // map of special offers, quantity - price
    private Map<Long, Integer> specialOffers = new HashMap<>();
    // map of free item offers, quantity -item sku
    private Map<Long, FreeItemOffer> freeItemOffers = new HashMap<>();

    public ItemBuilder sku(String sku) {
        this.sku = sku;
        return this;
    }

    public ItemBuilder price(Integer price) {
        this.defaultPrice = price;
        return this;
    }

    public ItemBuilder addSpecialOffer(Long quantity, Integer price) {
        this.specialOffers.put(quantity, price);
        return this;
    }

    public ItemBuilder addFreeItemOffer(Long quantity, FreeItemOffer offer) {
        this.freeItemOffers.put(quantity, offer);
        return this;
    }

    public Item build() {
        return new Item(
            sku,
            defaultPrice,
            specialOffers,
            freeItemOffers
        );
    }
}
