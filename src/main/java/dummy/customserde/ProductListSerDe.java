package dummy.customserde;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import dummy.model.Product;

public class ProductListSerDe implements JsonSerializer<List<Product>>,JsonDeserializer<List<Product>> {
	
	@Override
	public JsonElement serialize(List<Product> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonArray array = new JsonArray();
		src.forEach(p -> {
			JsonObject object = new JsonObject();
			object.addProperty("name", p.getName());
			object.addProperty("price", p.getPrice());
			array.add(object); 
		});
		return array;
	}

	@Override
	public List<Product> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return new Gson().fromJson(json.getAsJsonArray(), new TypeToken<List<Product>>() {}.getType());
	}
}
