package org.bu.test.butest.dto;

public record RoomAllocation(int occupied, double revenue) {
    public RoomAllocation addCustomer(double customerRequest) {
        return new RoomAllocation(this.occupied() + 1, this.revenue() + customerRequest);
    }
}
