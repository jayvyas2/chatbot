package com.chatbot.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.chatbot.dto.Details;
import com.chatbot.dto.Faqs;
import com.chatbot.dto.FeedbackDto;
import com.chatbot.dto.Order;
import com.chatbot.dto.Response;
import com.chatbot.dto.SupportContact;
import com.chatbot.model.ChatBotModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import opennlp.tools.doccat.DoccatModel;

@Service
public class ChatBotServiceImpl implements ChatBotService {

    @Override
    public Response getChatResponse(String request, String orderId) {
	Details details = new Details();
	ObjectMapper mapper = new ObjectMapper();
	details.setFaqs(new Faqs());
	Faqs faq = details.getFaqs();
	Map<String, String> faqMap = faq.getFaqs();

	try {

	    try {
		InputStream inputStream = ChatBotModel.class.getResourceAsStream("/details.json");
		File f = new File("./src/main/resources/details.json");
		FileUtils.copyInputStreamToFile(inputStream, f);
		details = mapper.readValue(f, Details.class);
	    } catch (StreamReadException e) {
		e.printStackTrace();
	    } catch (DatabindException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	} catch (JsonIOException e) {
	    e.printStackTrace();
	} catch (JsonSyntaxException e) {
	    e.printStackTrace();
	}
	Response response = new Response();
	String responseString = null;

	List<Order> orderList = details.getOrderList();

	switch (request) {
	case "support":
	case "support contact":
	case "support contact number":
	case "support contact phone":
	case "contact support":
	case "contact":
	case "email":
	case "e-mail":
	case "mail":
	case "support e-mail":
	case "support email":
	case "support mail":
	case "support contact details":
	    SupportContact sc = details.getSupportContact();
	    responseString = "E-mail: " + sc.getEmail() + ",\nContact: " + sc.getPhone();
	    response.setResponse(responseString);
	    break;

	case "order_list":
	case "orders":
	case "order list":
	case "orders list":
	case "list orders":
	case "list order":
	case "list of orders":
	case "list of order":

	    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	    try {
		responseString = ow.writeValueAsString(orderList);
		responseString = responseString.toString().replace("\\", "");
		responseString = responseString.toString().replace("\n", "");

		response.setResponse(responseString);
	    } catch (JsonProcessingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    break;

	case "status":
	case "order status":
	case "order_status":
	case "status of order":
	case "status of my order":
	case "my order status":

	    for (Order orderDetails : orderList) {
		if (orderDetails.getOrderId().equalsIgnoreCase(orderId)) {
		    responseString = orderDetails.getOrderStatus().toString();
		    response.setResponse(responseString);
		    break;
		}
	    }
	    break;

	case "refund":
	case "refund status":
	case "refund_status":
	case "where is my refund":
	case "status of refund":
	case "status of my refund":

	    for (Order orderDetails : orderList) {
		if (orderDetails.getOrderId().equalsIgnoreCase(orderId)) {
		    responseString = orderDetails.getRefundStatus().toString();
		    response.setResponse(responseString);
		    break;
		}
	    }
	    break;

	case "return":
	case "return status":
	case "return_status":
	case "where is my return":
	case "status of return":
	case "status of my return":

	    for (Order orderDetails : orderList) {
		if (orderDetails.getOrderId().equalsIgnoreCase(orderId)) {
		    responseString = orderDetails.getReturnStatus().toString();
		    response.setResponse(responseString);
		    break;
		}
	    }
	    break;

	case "delivery date":
	case "delivery_date":
	case "what is delivery date":
	case "when my order will be delivered":
	case "date of delivery":

	    for (Order orderDetails : orderList) {
		if (orderDetails.getOrderId().equalsIgnoreCase(orderId)) {
		    responseString = orderDetails.getDeliveryDate().toString();
		    response.setResponse(responseString);
		    break;
		}
	    }
	    break;

	}
	if (responseString == null || responseString.isEmpty()) {
	    DoccatModel model = null;
	    try {
		model = ChatBotModel.trainCategorizerModel();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    try {
		String[] lines = ChatBotModel.breakSentences(request);
		for (String line : lines) {

		    String[] tokenArray = ChatBotModel.tokenizeSentence(line);

		    String[] posTagArray = ChatBotModel.detectPOSTags(tokenArray);

		    String[] lemmaArray = ChatBotModel.lemmatizeTokens(tokenArray, posTagArray);

		    String category = ChatBotModel.detectCategory(model, lemmaArray);

		    category = category.replace('-', ' ');
		    for (Entry<String, String> entry : faqMap.entrySet()) {
			if (entry.getKey().contains(category)) {
			    responseString = faqMap.get(entry.getKey());
			    response.setResponse(responseString);
			    break;
			}
		    }
		    System.out.println("answer = ");
		    System.out.println(responseString);

		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}


	if (responseString == null)

	{
	    responseString = "Didn't get you. Try any question or 'support'";
	    response.setResponse(responseString);
	}
	return response;
    }

    @Override
    public Response saveFeedback(FeedbackDto feedbackDto, String orderId) {
	ObjectMapper mapper = new ObjectMapper();
	Details details = null;
	try {
	    InputStream inputStream = ChatBotModel.class.getResourceAsStream("/details.json");
	    File f = new File("./src/main/resources/details.json");
	    FileUtils.copyInputStreamToFile(inputStream, f);

	    details = mapper.readValue(f, Details.class);

	} catch (IOException e) {
	    e.printStackTrace();
	}

	for (Order order : details.getOrderList()) {
	    if (order.getOrderId().equals(orderId.toString())) {
		order.setFeedbackReceived(true);
		break;
	    }
	}

	mapper = new ObjectMapper();
	try {
	    InputStream inputStream = ChatBotModel.class.getResourceAsStream("/details.json");
	    File f = new File("./src/main/resources/details.json");
	    FileUtils.copyInputStreamToFile(inputStream, f);
	    mapper.writeValue(f, details);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	Response response = new Response();
	String responseString = "Feedback saved";
	response.setResponse(responseString);
	return response;
    }

}
