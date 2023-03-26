package dummy.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Animal {
	
	
	@SerializedName("breed")
	private String breed= getClass().getName();
	
	@SerializedName("sound")
	private String sound;
}
