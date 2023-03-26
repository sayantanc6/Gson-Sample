package dummy.instancecreator;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

@Component
public class LocalDateTimeInstanceCreator implements InstanceCreator<LocalDateTime> {

	@Override
	public LocalDateTime createInstance(Type type) {
		return LocalDateTime.now();
	}

}
