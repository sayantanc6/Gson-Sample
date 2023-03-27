package dummy.instancecreator;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.Discount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(OrderInstanceCreator.class)
@Component
public class DiscountInstanceCreator implements InstanceCreator<Discount> {
	
	@Value("${discount.value}")
	private float value;

	@Override
	public Discount createInstance(Type type) {
		return new Discount(value);
	}

}
