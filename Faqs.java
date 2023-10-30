package com.chatbot.dto;

import java.util.HashMap;
import java.util.Map;

public class Faqs {

    private Map<String, String> faqMap;

    public Faqs() {
	faqMap = new HashMap<String, String>();
	faqMap.put("how to place an order with us?",
		"To begin ordering, you may visit the us website on your desktop or mobile browser. \n        Steps to place an order:\n        1. Select the items which you want to order.\n        2. Add those items to your 'Shopping Cart'.\n        3. After reviewing your shopping cart, click on 'Proceed to Checkout'.\n        4. You’ll be prompted to sign in to us.in account, you can sign in using your mobile number or email address if you already have an account.\n        5. If you’re a new customer, click on 'I am a new customer' option on the sign in page to create a new account.\n        6. Enter the address where you want to receive your order/select the Pickup store as per the availability by searching in the available criteria.\n        7. If it a gift for someone/ if you want your order to be gift wrapped, then select gift-wrap option and include a gift message.\n        8. Select the payment method, add the details and click on 'Continue'.\n        9. Review your order and click on 'Place your Order' button and Pay to complete the transaction.\n        Note: The delivery speeds, dates and delivery address cannot be changed once an order is placed.");
	faqMap.put("how to change quantities of orders in the cart?",
		"To change the quantity for items you have not yet added to the cart -\nOnce you click on the 'Add to Cart' button and add the item to your shopping cart, enter the desired quantity in the 'Quantity' dropdown option on the right side of the page.\nTo change the quantity for items already added to the cart -\nGo to Cart, click on the quantity dropdown box to the right of the title and choose the desired quantity. The quantity number for the item and order amount will be updated automatically.\nIf the entered quantity is not available with us, you'll see an error message.");
	faqMap.put("how to place bulk orders?",
		"You can bulk order new products from our Business. \nYou can also access Bulk Ordering by hovering over your Account for Business menu and selecting Bulk Ordering from the drop-down menu. Enter the ISBNs or ASINs into the online form, or download a copy of the form to fill out and upload later.\nour Business offers the option to bulk buy products for business customers at no extra cost. You can also get an option of quantity discount.");
	faqMap.put("why am i unable to place orders?",
		"This could be due to the following issues:\nCheck if there is a Payment decline.\nThe item you have selected is out of stock.\nThere is some technical or internet connectivity issue.\nUndeliverable to selected location/pincode.\nCheck if there is a quantity limit.\nSometimes when FBA and seller fulfilled items are clubbed together.");
	faqMap.put("what is cash on delivery?",
		"Cash on Delivery is one of the payment methods for making purchases with us. When you select Cash on Delivery as your payment method, you don't have to make any advance payment. You pay for your order only when you receive it.");
	faqMap.put("how to place gift orders?",
		"You can choose to gift-wrap any item from your shopping cart as long as it is fulfilled by us. To do this, check the This will be a gift box next to the item that you want gift-wrapped. Alternatively, check the box next to Ordering a gift? during checkout. The gift options show on the Delivery & Payment page.\nProducts eligible for this service can be gift wrapped for just rs 30.");
	faqMap.put("how to change the payment method?",
		"You can add or update your payment methods by selecting the payments options section in Your Account. ");
	faqMap.put("can i change or modify orders that are already placed?",
		"You would not be able to change or modify the number of items once you have already placed an order. You can place another fresh order with the desired modifications. However, you can change your shipping preferences in Your Account at any time after placing your order as long as the order hasn't entered the shipping process yet.");
	faqMap.put("i ordered a wrong item. how can i stop the order?",
		"If you have ordered a wrong item, you can cancel the order. In case, the order is already shipped and out for delivery, you can return the order.");
	faqMap.put("how to cancel orders?",
		"You can cancel items or orders by visiting the Your Orders section in Your Account.\nTo cancel orders that aren't dispatched yet:\nGo to Your Orders.\nSelect the item you want to cancel and click Cancel items.\nProvide reasons for cancellation (optional).\nClick on Cancel Checked Items.\nTo cancel an order that has already been dispatched:\nGo to Your Orders.\nSelect the Request cancellation option and proceed further.\nThe item(s) will be returned to us for a refund (if the payment is already made).\nNote: In case you're still contacted for delivery, please refuse to accept it.");
	faqMap.put("how can i order the same items again?",
		"Follow the steps below to order an item again -\nGo to Your Orders.\nClick on the Buy Again Tab. You will see a list of items you had previously purchased.\nAdd to Cart the items you wish to purchase again.\nGo to the cart and follow the onscreen instruction to complete the order.\nNote: Items that are returned will not be displayed in the Buy Again tab.\n");
	faqMap.put("how to track orders?",
		"The products ordered by you on us.in are delivered in following ways:\nOur fulfilled delivery\nSeller fulfilled delivery\nDelivery by third party carriers");

	faqMap.put("greeting", "Hello, how can I help you?");

    }

    public Map<String, String> getFaqs() {
	return faqMap;
    }

    public void setFaqs(Map<String, String> faqs) {
	this.faqMap = faqs;
    }

}
