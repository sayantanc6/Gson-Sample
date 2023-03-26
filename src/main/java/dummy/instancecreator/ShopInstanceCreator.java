package dummy.instancecreator;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.Shop;

@Component
public class ShopInstanceCreator implements InstanceCreator<Shop> {

	@Value("${shop.name}")
	private String name;
	
	@Value("${shop.sellername}")
	private String sellerName;
	
	@Override
	public Shop createInstance(Type type) {
		return new Shop(name, sellerName);
	}

}
