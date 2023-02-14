package com.store.enums;

public enum OrderStatus {
    NotShipped("Shipment is pending"),
    Shipped("Shipment has been released for the destination "),
    Delivered("Delivered");

    private String orderStatus;

    private OrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
}
