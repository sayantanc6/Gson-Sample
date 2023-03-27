package dummy.instancecreator;

import java.lang.reflect.Type;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(OrderInstanceCreator.class)
@Component
public class LocalDateInstanceCreator implements InstanceCreator<LocalDate> {

	@Override
	public LocalDate createInstance(Type type) {
		return LocalDate.now();
	}
}
