package dummy.instancecreator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(OrderInstanceCreator.class)
@Component
public class OrderProductsInstanceCreator implements InstanceCreator<List<Product>> {
	
	@Autowired
	ProductInstanceCreator productcreator;
	
	List<Product> products = new ArrayList<Product>();

	@Override
	public List<Product> createInstance(Type type) { 
		products.add(productcreator.createInstance(Product.class));
		products.add(productcreator.createInstance(Product.class));
		products.add(productcreator.createInstance(Product.class));
		return products.stream().distinct().collect(Collectors.toList()); // this is just a demo,you can pull the database via repository.
	}

}
