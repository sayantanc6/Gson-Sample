package dummy.instancecreator;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

import dummy.model.DeliveryData;
import dummy.model.Discount;
import dummy.model.Order;
import dummy.model.OrderStatus;
import dummy.model.PaymentType;
import dummy.model.Product;
import dummy.model.Shop;
import dummy.model.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;


@ConditionalOnProperty(name = "instancecreatorbeansexist",havingValue = "yes")
@Component
public class OrderInstanceCreator implements InstanceCreator<Order> {
	
	@Autowired
	LocalDateTimeInstanceCreator ldtinstcreator;
	
	@Autowired
	DiscountInstanceCreator discountcreator;
	
	@Autowired
	LocalDateInstanceCreator ldinstancecreator;
	
	@Value("${orderid}")
	private String orderid;
	
	@Value("${orderstatus}")
	private String orderstatus;
	private OrderStatus type_;
	
	@Autowired
	ProductInstanceCreator productinstancecreator;
	
	@Autowired
	ShopInstanceCreator shopinstancecreator;
	
	@Autowired
	UserInstanceCreator userinstancecreator;
	
	@Value("${paymenttype}")
	private String paymenttype;
	private PaymentType type;
	
	@Autowired
	DeliveryDataInstanceCreator ddinstancecreator;
	
	@Autowired
	OrderProductsInstanceCreator orderproductsinstancecreator;

	@Override
	public Order createInstance(Type type) {
		return new Order(ldtinstcreator.createInstance(LocalDateTime.class), 
				getPaymenttype(paymenttype), 
				discountcreator.createInstance(Discount.class), 
				ddinstancecreator.createInstance(DeliveryData.class), 
				userinstancecreator.createInstance(User.class), 
				orderproductsinstancecreator.createInstance(new TypeToken<List<Product>>() {}.getType()), 
				shopinstancecreator.createInstance(Shop.class), 
				Integer.parseInt(orderid),  
				getOrderStatus(orderstatus), 
				ldinstancecreator.createInstance(LocalDate.class));
	}

	private OrderStatus getOrderStatus(String orderstatus2) {
		orderstatus = orderstatus2.toUpperCase();
		
		switch (orderstatus) {
		case "PROCESSING":
			this.type_= OrderStatus.PROCESSING;
			break;
		case "BUILD":
			this.type_= OrderStatus.BUILD;
			break;
		case "SHIPPED":
			this.type_ = OrderStatus.SHIPPED;
			break;
		case "DELIVERED":
			this.type_ = OrderStatus.DELIVERED;
			break;
		default:
			this.type_ = null;
			break;
		}
		return this.type_;
	}

	private PaymentType getPaymenttype(String paymenttype2) {
		paymenttype = paymenttype2.toUpperCase();
		
		switch (paymenttype) { 
		case "CASH":
			this.type = PaymentType.CASH;
			break;
		case "CARD":
			this.type = PaymentType.CARD;
			break;
		default:
			this.type = null;
			break;
		}
		return this.type;
	}
}
