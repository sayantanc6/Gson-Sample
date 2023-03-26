package dummy.instancecreator;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.Product;

@Component
public class ProductInstanceCreator implements InstanceCreator<Product> {

	@Value("${product.name}")
	private String name;
	
	@Value("${product.price}")
	private double price;
	
	@Override
	public Product createInstance(Type type) {
		return new Product(name, price);
	}
}
