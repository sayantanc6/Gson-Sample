package dummy.instancecreator;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.DeliveryData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(OrderInstanceCreator.class)
@Component
public class DeliveryDataInstanceCreator implements InstanceCreator<DeliveryData> {

	@Value("${delivered.numOfPackages}")
	private int numOfPackages;
	
	@Override
	public DeliveryData createInstance(Type type) {
		return new DeliveryData(numOfPackages);
	}

}
