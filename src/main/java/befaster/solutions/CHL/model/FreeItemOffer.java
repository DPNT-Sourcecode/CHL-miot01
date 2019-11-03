package befaster.solutions.CHL.model;

public class FreeItemOffer {

    private String offeredItem;
    private Long quantity;
    private Long requiredQuantity;

    public FreeItemOffer(String offeredItem, Long quantity, Long requiredQuantity) {
        this.offeredItem = offeredItem;
        this.quantity = quantity;
        this.requiredQuantity = requiredQuantity;
    }

    public String getOfferedItem() {
        return offeredItem;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getRequiredQuantity() {
        return requiredQuantity;
    }
}

