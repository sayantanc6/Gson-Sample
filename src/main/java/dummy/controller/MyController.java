package dummy.controller;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dummy.instancecreator.ArtistModelListInstanceCreator;
import dummy.instancecreator.OrderInstanceCreator;
import dummy.model.Animal;
import dummy.model.ArtistModel;
import dummy.model.Cow;
import dummy.model.Director;
import dummy.model.Dog;
import dummy.model.Employee;
import dummy.model.GenericBox;
import dummy.model.Order;
import dummy.model.UserNested;

	
@RestController
public class MyController {
	
	@Autowired
	private Gson gson;
	
	Set<ArtistModel> artistset;
    List<Animal> animals;
	Map<Long, ArtistModel> artistmap;
	StringBuilder builder;
	
	@Value("${instancecreatorbeansexist}")
	String instcreatorsexist;
    
    @Autowired
    AbstractApplicationContext ctx;

    // Here JsonAdapter won't work,because we're not deserializing SampleController.java!
    //@JsonAdapter(ArtistModelListDeser.class)
    List<ArtistModel> artists;
    Order order;
    
    GenericBox<ArtistModel> artistmodelbox;

	
	@PostMapping(value = "/simpleobjectusernestedserde",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserNested simpleobjectusernestedserde(@RequestBody UserNested obj) {
		/* 
		 * sample JSON request body without instance creator,
		 * but with instance creator it's not needed as you've a 
		 * customised parameterised constructors in-built.
		 * 
		 * {
		 *   "name" : "sayantan",
		 *   "email" : "sayantanc6@gmail.com",
		 *   "isDeveloper" : true,
		 *   "age": 34,
		 *   "userAddress" : {
		 *     "street" : "sitala tala",
		 *     "houseNumber" : "18/A",
		 *      "city": "Kolkata",
		 *      "country" : "India"
		 *   }
		 * }
		 */
		System.out.println(obj); 
		String input = gson.toJson(obj,UserNested.class);
		System.out.println("nested ser : \n"+input); 
		UserNested nested_ = gson.fromJson(input, UserNested.class);
		System.out.println("nested deser : \n"+nested_);
		return gson.fromJson(gson.toJson(obj), UserNested.class);  

	}
	
	@PostMapping(value = "/complexobjectorderserde",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Order complexobjectorderserde(@RequestBody Order obj) {
		/*
		 * Sample JSON request body without instance creator,
		 * but with instance creator it's not needed as you've a 
		 * customised parameterised constructors in-built.
		 * 
		 * {
		 *	    "orderFinishDate" : "2013-03-25T12:06:06",
		 *      "paymentType" : "CARD",
		 *      "discount" : {"value" : 0.25},
		 *	    "deliveryData" : {"numOfPackages" : 67},
		 *    "orderingUser" : {
		 *        "firstname" : "sayantan",
		 *        "lastname" : "chatterjee"
		 *      },
		 *    "orderedProducts" : [
		 *	        {"name" : "ordername","price" : 74.00},
		 *	         {"name" : "ordername_","price" : 75.00}
		 *	    ],
		 *	    "offeringShop" : {
		 *	        "name" : "shopname",
		 *	        "sellerName" : "sellerName"},
		 *	    "orderId" : 67,
		 *	    "orderstatus" : "PROCESSING",
		 *	    "orderDate" : "2018-10-26"
		 *	}
		 */
		System.out.println(obj);
		String input = gson.toJson(obj, Order.class);
		System.out.println("order ser : \n"+input); 
		
	   if (instcreatorsexist.equals("yes")) {
			OrderInstanceCreator ordercreator = ctx.getBean(OrderInstanceCreator.class);
			Order order = ordercreator.createInstance(new TypeToken<Order>() {}.getType());
			System.out.println("order deser : \n" + order);
			return order;
	   }
		return gson.fromJson(input, Order.class);
	}
	
	@ConditionalOnProperty(name = "instancecreatorbeansexist",havingValue = "yes")
	@PostMapping(value = "/createnestedinstancecreators",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void createnestedinstancecreators() {
		if (instcreatorsexist.equals("yes")) {
			OrderInstanceCreator ordercreator = ctx.getBean(OrderInstanceCreator.class);
			this.order = ordercreator.createInstance(Order.class);
			System.out.println(this.order);
			System.out.println("order ser : \n");
			String orderser = gson.toJson(this.order);
			System.out.println(orderser);
		}
	}
	
	@GetMapping(value = "gsonserde",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=application/json")
	public void gsonserde() {
		if (instcreatorsexist.equals("yes")) {
			ArtistModelListInstanceCreator cArtistModelListInstanceCreator = ctx.getBean(ArtistModelListInstanceCreator.class);
			this.artists = cArtistModelListInstanceCreator.createInstance(new TypeToken<List<ArtistModel>>() {}.getType());
		}else {
			this.artists = Arrays.asList(new ArtistModel(1L, "abc", "34", "catch1", "desc1", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null),
										 new ArtistModel(2L, "abc1", "44", "catch2", "desc2", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null),
										 new ArtistModel(3L, "abc2", "54", "catch3", "desc3", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null));
		}
		String jsonout  = gson.toJson(this.artists);
		System.out.println("json ser :-");
		System.out.println(jsonout);
		
		artists = gson.fromJson(jsonout, new TypeToken<List<ArtistModel>>() {}.getType());
		System.out.println("json deser :-");
		System.out.println(this.artists);
		
	}
	
	@GetMapping(value = "gsonsermap",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=application/json")
	public void getgsonmap() {
		Map<Long, ArtistModel> map = new HashMap<Long, ArtistModel>();
		if (instcreatorsexist.equals("yes")) {
			ArtistModelListInstanceCreator cArtistModelListInstanceCreator = ctx.getBean(ArtistModelListInstanceCreator.class);
			this.artists = cArtistModelListInstanceCreator.createInstance(new TypeToken<List<ArtistModel>>() {}.getType()); 
		}else {
			this.artists = Arrays.asList(new ArtistModel(1L, "abc", "34", "catch1", "desc1", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null),
										 new ArtistModel(2L, "abc1", "44", "catch2", "desc2", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null),
										 new ArtistModel(3L, "abc2", "54", "catch3", "desc3", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null));
		}
		map = this.artists.stream().collect(Collectors.toMap(ArtistModel::getArtistId, Function.identity())); 
		
		String jsonmap  = gson.toJson(map); 
		System.out.println("json ser map :-");
		System.out.println(jsonmap);
		
		map = gson.fromJson(jsonmap, new TypeToken<Map<Long,ArtistModel>>() {}.getType());
		//or there another way around..
	//	map = gson.fromJson(jsonmap, TypeToken.getParameterized(HashMap.class,Long.class,ArtistModel.class).getType());
		System.out.println("json deser map:-"); 
		System.out.println(map);
	}
	
	@GetMapping(value = "gsonlistsetmapserde",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=application/json")
	public void gsonlistsetmapserde() throws IOException {
		
        if (instcreatorsexist.equals("yes")) {
    		ArtistModelListInstanceCreator cArtistModelListInstanceCreator = ctx.getBean(ArtistModelListInstanceCreator.class);
			this.artists = cArtistModelListInstanceCreator.createInstance(new TypeToken<List<ArtistModel>>() {}.getType());
        }else {
        	this.artists = Arrays.asList(new ArtistModel(1L, "abc", "34", "catch1", "desc1", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null),
										 new ArtistModel(2L, "abc1", "44", "catch2", "desc2", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null),
										 new ArtistModel(3L, "abc2", "54", "catch3", "desc3", new GregorianCalendar(2023, Calendar.DECEMBER, 7).toZonedDateTime().toLocalDateTime(), null));
		}
        
		System.out.println("artist list : "+this.artists);

		String jsonout  = gson.toJson(this.artists);
		System.out.println("json list ser :-");
		System.out.println(jsonout);
		
		this.artists = gson.fromJson(jsonout, new TypeToken<List<ArtistModel>>() {}.getType());
		System.out.println("json list deser :-");
		System.out.println(this.artists);
		
		System.out.println("object convert list to set :- ");
		this.artistset = new HashSet<>();
		this.artistset = this.artists.stream().collect(Collectors.toSet());
		System.out.println("artist set : "+this.artistset);
		System.out.println("json ser set :- "); 
		String jsonserset = gson.toJson(this.artistset); 
		System.out.println(jsonserset);
		System.out.println("json deser set :- ");
		this.artistset = gson.fromJson(jsonserset, new TypeToken<Set<ArtistModel>>() {}.getType());
		System.out.println(this.artistset);
		
		// write to file
		String filename ="src/main/resources/artists.json";
		Path path = Paths.get(filename);
		try(Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
		     gson.toJson(this.artists,writer);
		     System.out.println("artists written to file in {}"+filename);
		}
		
		//read from file
		String filename_ ="src/main/resources/artists.json";
		Path path_ = Paths.get(filename_);
		try(Reader reader = Files.newBufferedReader(path_, StandardCharsets.UTF_8)){
		     this.artists = gson.fromJson(reader,new TypeToken<List<ArtistModel>>() {}.getType());
		     System.out.printf("artists read from %s in {} :\n",filename_,this.artists);
		     this.artists.forEach(System.out::println); 
		}
		
		this.artistmap = new HashMap<Long, ArtistModel>();
		System.out.println("object convert set to map :- ");
		this.artistmap = this.artistset.stream().collect(Collectors.toMap(ArtistModel::getArtistId,Function.identity()));
		System.out.println("artist map : "+this.artistmap); 
		System.out.println("json ser map :- "); 
		String jsonsermap = gson.toJson(this.artistmap);
		System.out.println(jsonsermap);
		System.out.println("json deser map :- "); 
		this.artistmap = gson.fromJson(jsonsermap, new TypeToken<Map<Long, ArtistModel>>(){}.getType());
		System.out.println(this.artistmap);
		
		// Not needed,just to make pretty printing in json
		builder = new StringBuilder();
		builder.append("[\n\t");
		int counter = 0;
		for (ArtistModel artistModel : artists) {
			counter++;
			builder.append("{\n\t\t")
			.append("\"ArtistId\""+" : ").append(artistModel.getArtistId()+",\n\t\t")
			.append("\"name\""+" : ").append(artistModel.getName()+",\n\t\t")
			.append("\"age_\""+" : ").append(artistModel.getAge()+",\n\t\t")
			.append("\"catchphrase\""+" : ").append(artistModel.getCatchphrase()+",\n\t\t")
			.append("\"description\""+" : ").append(artistModel.getDescription()+",\n\t\t")
			.append("\"date\""+" : ").append(artistModel.getDate()+",\n\t\t") 
			.append("\"director\""+" : ").append(artistModel.getDirector()+",\n\t");
			if (artists.size() > counter) {
				builder.append("},\n\t");
			}else {
				builder.append("}\n");
			}
		}
		String jsonpp = builder.append("]").toString();
		System.out.println("json pretty printing : \n"+jsonpp);
	}
	
	@GetMapping(value = "hierarchicalserde",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=application/json")
	public void polymorphicserde() {
		this.animals = Arrays.asList(new Cow("Cow","moo"),new Dog("dog","bark")); 
		System.out.println("json ser polymorphic : ");
		String animalser = gson.toJson(this.animals);
		System.out.println(animalser);
		 
		System.out.println("json deser polymorphic : ");

	    List<Animal> outList = gson.fromJson(animalser, new TypeToken<List<Animal>>(){}.getType());
		System.out.println(outList);
	}

        
         @GetMapping(value = "nestedinnerserde",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=application/json")
	public void nestedinnerserde() {
		
		Employee employee = new Employee("sayantan", "chatterjee",new Employee.InnerEmployee("innersayantan", "innerchatterjee"));
		
		System.out.println("nested inner ser : ");
		String nestedser = gson.toJson(employee);
		System.out.println(nestedser);
		
		System.out.println("nested inner deser : ");
		Employee employee2 = gson.fromJson(nestedser, Employee.class);
		System.out.println(employee2);
	}
       

	@GetMapping(value = "genericsserde",consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=application/json")
		public void genericsserde() { 
			
			if (instcreatorsexist.equals("yes")) {
				ArtistModelListInstanceCreator cArtistModelListInstanceCreator = ctx.getBean(ArtistModelListInstanceCreator.class);
				this.artistmodelbox = new GenericBox<ArtistModel>(new ArtistModel(7L, "gen", "67", "gencatch", "gendesc",
						new GregorianCalendar(2023, Calendar.OCTOBER, 23).toZonedDateTime().toLocalDateTime(),
						new Director(cArtistModelListInstanceCreator.createInstance(new TypeToken<List<ArtistModel>>() {}.getType()))));
			}else {
				this.artistmodelbox = new GenericBox<ArtistModel>(new ArtistModel(7L, "gen", "67", "gencatch", "gendesc",
						new GregorianCalendar(2023, Calendar.OCTOBER, 23).toZonedDateTime().toLocalDateTime(),null));
			}
			System.out.println("generics ser : ");
			String artmodboxstr = gson.toJson(this.artistmodelbox);
			System.out.println(artmodboxstr);
			
			System.out.println("generics deser : ");
			GenericBox<ArtistModel> model = gson.fromJson(artmodboxstr, new TypeToken<GenericBox<ArtistModel>>() {}.getType());
			System.out.println(model);
		}
}
