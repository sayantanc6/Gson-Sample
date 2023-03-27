package dummy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import dummy.customserde.ProductListSerDe;
import org.springframework.stereotype.Component;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import dummy.customserde.LocalDateSerDe;
import dummy.customserde.LocalDateTimeSerDe;
import dummy.customserde.ProductListSer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@JsonAdapter(LocalDateTimeSerDe.class)
	@SerializedName("orderFinishDate")
	private LocalDateTime orderFinishDate;
	
	@SerializedName("paymentType")
    private PaymentType paymentType;
	
	@SerializedName("discount")
    private Discount discount;
	
	@SerializedName("deliveryData")
    private DeliveryData deliveryData;
	
	@SerializedName("orderingUser")
    private User orderingUser;
	
	@JsonAdapter(ProductListSerDe.class)
	@SerializedName("orderedProducts")
    private List<Product> orderedProducts;
	
	@SerializedName("offeringShop")
    private Shop offeringShop;
	
	@SerializedName("orderId")
    private int orderId;
	
	@SerializedName("status")
    private OrderStatus status;
	
	@JsonAdapter(LocalDateSerDe.class)
	@SerializedName("orderDate")
    private LocalDate orderDate;
}
