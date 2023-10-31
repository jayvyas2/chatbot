package com.chatbot.service;

import com.chatbot.dto.FeedbackDto;
import com.chatbot.dto.Response;

public interface ChatBotService {

    public Response getChatResponse(String request, String orderId);

    public Response saveFeedback(FeedbackDto feedbackDto, String orderId);

}
