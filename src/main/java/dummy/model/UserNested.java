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
public class UserNested {

	@SerializedName("name")
	private String name;
	
	@SerializedName("email")
	private String email;
	
	@SerializedName("isDeveloper")
	private boolean isDevelopers;
	
	@SerializedName("age")
	private int age;
	
	@SerializedName("userAddress")
	UserAddress userAddress;
}
