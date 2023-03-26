package dummy.instancecreator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.InstanceCreator;

import dummy.model.ArtistModel;

@Component
public class ArtistModelListInstanceCreator implements InstanceCreator<List<ArtistModel>> {
	
	List<ArtistModel> artists = new ArrayList<ArtistModel>();

	@Override
	public List<ArtistModel> createInstance(Type type) {
		
		this.artists.add(new ArtistModel(15L, "abcI", "34", "catch1", "desc1",new GregorianCalendar(2014, Calendar.FEBRUARY, 11).toZonedDateTime().toLocalDateTime(),null));
		this.artists.add(new ArtistModel(16L, "defI", "", "catch2", "desc2",new GregorianCalendar(2014, Calendar.FEBRUARY, 11).toZonedDateTime().toLocalDateTime(),null));
		this.artists.add(new ArtistModel(17L, "ghiI", null, "catch3", null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).toZonedDateTime().toLocalDateTime(),null));
		return artists;
	}

}
