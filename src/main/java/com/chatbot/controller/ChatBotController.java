package com.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.dto.FeedbackDto;
import com.chatbot.dto.Response;
import com.chatbot.service.ChatBotService;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController {
    @Autowired
    ChatBotService chatBotService;

    @GetMapping(value = "/request")
    public ResponseEntity<Response> getChatBotResponse(
	    @RequestParam(value = "request_string", required = true) String requestString,
	    @RequestParam(value = "order_id", required = false) String orderId) {
	Response res = chatBotService.getChatResponse(requestString, orderId);
	return new ResponseEntity<Response>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> takeFeedback(@RequestParam(value = "order_id", required = true) String orderId,
	    @RequestBody FeedbackDto feeedback) {
	Response res = chatBotService.saveFeedback(feeedback, orderId);
	return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
}
