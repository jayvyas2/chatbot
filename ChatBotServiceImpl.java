package com.chatbot.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.chatbot.dto.Details;
import com.chatbot.dto.Faqs;
import com.chatbot.dto.FeedbackDto;
import com.chatbot.dto.Order;
import com.chatbot.dto.Response;
import com.chatbot.dto.SupportContact;
import com.chatbot.model.ChatBotModel;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		File f = new File("./src/main/resources/details.json");
//		details = mapper.readValue(
//			new File("/Users/jayvyas/Documents/MS/5th Sem/project/chatbot/src/main/resources/details.json"),
//			Details.class);
		details = mapper.readValue(f, Details.class);
	    } catch (StreamReadException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (DatabindException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	} catch (JsonIOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JsonSyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Response response = new Response();
	String responseString = null;

	List<Order> orderList = details.getOrderList();
	DoccatModel model = null;
	try {
	    model = ChatBotModel.trainCategorizerModel();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try {
	    String[] sentences = ChatBotModel.breakSentences(request);
	    for (String sentence : sentences) {

		// Separate words from each sentence using tokenizer.
		String[] tokens = ChatBotModel.tokenizeSentence(sentence);

		// Tag separated words with POS tags to understand their gramatical structure.
		String[] posTags = ChatBotModel.detectPOSTags(tokens);

		// Lemmatize each word so that its easy to categorize.
		String[] lemmas = ChatBotModel.lemmatizeTokens(tokens, posTags);

		// Determine BEST category using lemmatized tokens used a mode that we trained
		// at start.
		String category = ChatBotModel.detectCategory(model, lemmas);

		// Get predefined answer from given category & add to answer.
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

		// If category conversation-complete, we will end chat conversation.
//		if ("conversation-complete".equals(category)) {
//			conversationComplete = true;
//		}
	    }

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	switch (request) {
	case "support":
	case "contact":
	case "email":
	case "e-mail":
	case "mail":
	    SupportContact sc = details.getSupportContact();
	    responseString = "E-mail: " + sc.getEmail() + ", Contact: " + sc.getPhone();
	    response.setResponse(responseString);
	    break;

	case "order_list":
	    responseString = orderList.toString();
	    response.setResponse(responseString);
	    break;

	case "status":
	    for (Order orderDetails : orderList) {
		if (orderDetails.getOrderId().equalsIgnoreCase(orderId)) {
		    responseString = orderDetails.getOrderStatus().toString();
		    response.setResponse(responseString);
		    break;
		}
	    }
	    break;

	case "refund":
	    for (Order orderDetails : orderList) {
		if (orderDetails.getOrderId().equalsIgnoreCase(orderId)) {
		    responseString = orderDetails.getRefundStatus().toString();
		    response.setResponse(responseString);
		    break;
		}
	    }
	    break;

	}
	if (responseString == null) {
//	    details.setFaqs(new Faqs());
//	    Faqs faq = details.getFaqs();
//	    Map<String, String> faqMap = faq.getFaqs();

	    for (Entry<String, String> entry : faqMap.entrySet()) {
		if (entry.getKey().contains(request)) {
		    responseString = faqMap.get(entry.getKey());
		    response.setResponse(responseString);
		    return response;
		}
	    }
	}

	if (responseString == null) {
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
	    File f = new File("./src/main/resources/details.json");
//		details = mapper.readValue(
//			new File("/Users/jayvyas/Documents/MS/5th Sem/project/chatbot/src/main/resources/details.json"),
//			Details.class);
	    details = mapper.readValue(f, Details.class);
//	    details = mapper.readValue(
//		    new File("/Users/jayvyas/Documents/MS/5th Sem/project/chatbot/src/main/resources/details.json"),
//		    Details.class);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	for (Order order : details.getOrderList()) {
	    if (order.getOrderId().equals(orderId.toString())) {
		order.setFeedbackReceived(true);
		break;
	    }
	}
//	try (FileWriter file = new FileWriter(
//		"/Users/jayvyas/Documents/MS/5th Sem/project/chatbot/src/main/resources/details.json")) {
//	    file.write(details.toString());
//	    System.out.println("Successfully updated json object to file...!!");
//	} catch (IOException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
	mapper = new ObjectMapper();
	try {
	    File f = new File("./src/main/resources/details.json");
	    mapper.writeValue(f, details);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Response response = new Response();
	String responseString = "Feedback saved";
	response.setResponse(responseString);
	return response;
    }

}
