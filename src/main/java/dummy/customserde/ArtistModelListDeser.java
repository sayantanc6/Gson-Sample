package dummy.customserde;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import dummy.model.ArtistModel;

public class ArtistModelListDeser implements JsonDeserializer<List<ArtistModel>> {

	@Override
	public List<ArtistModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)throws JsonParseException {
		List<ArtistModel> artists = new Gson().fromJson(json.getAsJsonArray(), new TypeToken<List<ArtistModel>>() {}.getType());
		return artists.stream().distinct().collect(Collectors.toList());
	}

}
