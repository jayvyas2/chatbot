package com.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedbackDto {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("rate_product")
    private String rateProduct;

    @JsonProperty("rate_delivery")
    private String rateDelivery;

    @JsonProperty("order_feedback")
    private String orderFeedback;

    @JsonProperty("suggestion")
    private String suggestion;

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public String getRateProduct() {
	return rateProduct;
    }

    public void setRateProduct(String rateProduct) {
	this.rateProduct = rateProduct;
    }

    public String getRateDelivery() {
	return rateDelivery;
    }

    public void setRateDelivery(String rateDelivery) {
	this.rateDelivery = rateDelivery;
    }

    public String getOrderFeedback() {
	return orderFeedback;
    }

    public void setOrderFeedback(String orderFeedback) {
	this.orderFeedback = orderFeedback;
    }

    public String getSuggestion() {
	return suggestion;
    }

    public void setSuggestion(String suggestion) {
	this.suggestion = suggestion;
    }

}
