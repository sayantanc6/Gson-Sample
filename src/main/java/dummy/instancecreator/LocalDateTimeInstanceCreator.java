package dummy.instancecreator;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(OrderInstanceCreator.class)
@Component
public class LocalDateTimeInstanceCreator implements InstanceCreator<LocalDateTime> {

	@Override
	public LocalDateTime createInstance(Type type) {
		return LocalDateTime.now();
	}

}
