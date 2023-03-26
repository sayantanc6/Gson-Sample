package dummy.model;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dog extends Animal {

	@SerializedName("dogbreed")
	private String breed;
	
	@SerializedName("dogsound")
	private String sound;

	public Dog(String breed, String sound, String breed2, String sound2) {
		super(breed, sound);
		breed = breed2;
		sound = sound2;
	}
}
