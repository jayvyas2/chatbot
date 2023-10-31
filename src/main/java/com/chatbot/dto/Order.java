package com.chatbot.dto;

import java.util.Date;

import com.chatbot.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("status")
    private Status orderStatus;

    @JsonProperty("delivery_date")
    private Date deliveryDate;

    @JsonProperty("details")
    private String productDetails;

    @JsonProperty("refund")
    private Status refundStatus;

    @JsonProperty("return")
    private Status returnStatus;

    @JsonProperty("name")
    private String name;

    @JsonProperty("feedback_received")
    private boolean feedbackReceived;

    public Status getOrderStatus() {
	return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
	this.orderStatus = orderStatus;
    }

    public Date getDeliveryDate() {
	return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
	this.deliveryDate = deliveryDate;
    }

    public String getProductDetails() {
	return productDetails;
    }

    public void setProductDetails(String productDetails) {
	this.productDetails = productDetails;
    }

    public Status getRefundStatus() {
	return refundStatus;
    }

    public void setRefundStatus(Status refundStatus) {
	this.refundStatus = refundStatus;
    }

    public Status getReturnStatus() {
	return returnStatus;
    }

    public void setReturnStatus(Status returnStatus) {
	this.returnStatus = returnStatus;
    }

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isFeedbackReceived() {
	return feedbackReceived;
    }

    public void setFeedbackReceived(boolean feedbackReceived) {
	this.feedbackReceived = feedbackReceived;
    }

    @Override
    public String toString() {
	return "Order [order_id=" + orderId + ", order_status=" + orderStatus + ", delivery_date=" + deliveryDate
		+ ", product_details=" + productDetails + ", refund_status=" + refundStatus + ", return_status="
		+ returnStatus + ", name=" + name + ", feedback_received=" + feedbackReceived + "]";
    }



}
