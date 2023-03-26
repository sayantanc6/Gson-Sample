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
public class UserAddress {
	
	@SerializedName("street")
	private String street;
	
	@SerializedName("houseNumber")
	private String houseNumber;
	
	@SerializedName("city")
	private String city;
	
	@SerializedName("country")
	private String country;

}
