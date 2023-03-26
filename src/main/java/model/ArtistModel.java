package dummy.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.SerializedName;

import dummy.customserde.LocalDateTimeSerDe;

import com.google.gson.annotations.JsonAdapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistModel {

	@SerializedName("ArtistId")
	private Long ArtistId;
	
	@NotNull(message = "Name is required")
	@SerializedName("name")
	private String name;
	
	@NotNull(message = "Age is required")
	@Min(0)
	@Max(100)
	@SerializedName("age")
	private String age;
	
	@NotNull(message = "catchphrase is required")
	@SerializedName("catchphrase")
	private String catchphrase;
	
	@NotNull(message = "description is required")
	@SerializedName("description")
	private String description;
         
       
    @JsonAdapter(LocalDateTimeSerDe.class)
	@SerializedName("date")
	private LocalDateTime date;
	
	@SerializedName("director")
	private Director director;

}
