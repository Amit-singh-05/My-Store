package com.store.enums;

public enum OrderStatus {
	Delivered("Delivered",3),
    Shipped("Shipment has been released for the destination ",2),
    NotShipped("Shipment is pending",1);

    private String orderStatus;

    private OrderStatus(String orderStatus, int i) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
}
