package befaster.solutions.CHL.model;

public class FreeItemOffer {

    private String offeredItem;
    private Long quantity;
    private String requiredQuantity;

    public FreeItemOffer(String offeredItem, Long quantity, String requiredQuantity) {
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

    public String getRequiredQuantity() {
        return requiredQuantity;
    }
}
