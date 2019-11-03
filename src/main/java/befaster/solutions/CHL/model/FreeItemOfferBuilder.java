package befaster.solutions.CHL.model;

public class FreeItemOfferBuilder {
    private String offeredItem;
    private Long quantity;
    private Long requiredQuantity;

    public FreeItemOfferBuilder item(String item) {
        this.offeredItem = item;
        return this;
    }

    public FreeItemOfferBuilder quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public FreeItemOfferBuilder requiredQuantity(Long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
        return this;
    }

    public FreeItemOffer build() {
        return new FreeItemOffer(offeredItem, quantity, requiredQuantity);
    }
}
