package dummy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	private String first_name;
	private String last_name;
	private InnerEmployee Employee;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InnerEmployee {
		
		private String inner_first_name;
		private String inner_last_name;
	}

}
