package dummy.customserde;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import dummy.model.Product;

public class ProductListSer implements JsonSerializer<List<Product>> {
	
	List<Product> products= new ArrayList<Product>();
	
	@Override
	public JsonElement serialize(List<Product> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		src.forEach(p -> {
			obj.addProperty("name", p.getName());
			obj.addProperty("price", p.getPrice());
		});
		return obj;
	}
}
