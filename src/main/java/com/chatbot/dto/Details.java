package com.chatbot.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Details {

    private Faqs faqs;

    @JsonProperty("order")
    private List<Order> orderList;

    @JsonProperty("support_contact")
    private SupportContact supportContact;

    public Faqs getFaqs() {
	return faqs;
    }

    public void setFaqs(Faqs faqs) {
	this.faqs = faqs;
    }

    public List<Order> getOrderList() {
	return orderList;
    }

    public void setOrderList(List<Order> orderList) {
	this.orderList = orderList;
    }

    public SupportContact getSupportContact() {
	return supportContact;
    }

    public void setSupportContact(SupportContact supportContact) {
	this.supportContact = supportContact;
    }

}
