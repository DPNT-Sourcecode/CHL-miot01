package befaster.solutions.CHL.model;

public class AvailableFreeOffer {
    private FreeItemOffer offer;
    private Long quantity;

    public AvailableFreeOffer(FreeItemOffer offer, Long quantity) {
        this.offer = offer;
        this.quantity = quantity;
    }

    public FreeItemOffer getOffer() {
        return offer;
    }

    public void setOffer(FreeItemOffer offer) {
        this.offer = offer;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getRequiredQuantity() {
        return offer.getRequiredQuantity() * quantity;
    }
}


