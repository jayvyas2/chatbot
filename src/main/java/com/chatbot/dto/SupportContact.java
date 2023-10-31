package com.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportContact {

    @JsonProperty("e-mail")
    private String email;

    private int phone;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public int getPhone() {
	return phone;
    }

    public void setPhone(int phone) {
	this.phone = phone;
    }

}
