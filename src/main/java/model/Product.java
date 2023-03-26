package dummy.model;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@SerializedName("name")
	private String name;
	
	@SerializedName("price")
	private double price;
}
