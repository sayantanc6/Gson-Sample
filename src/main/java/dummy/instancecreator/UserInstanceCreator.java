package dummy.instancecreator;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.User;

@Component
public class UserInstanceCreator implements InstanceCreator<User> {

	@Value("${user.firstname}")
	private String firstname;
	
	@Value("${user.lastname}")
	private String lastname;

	@Override
	public User createInstance(Type type) {
		return new User(firstname, lastname);
	}

}
