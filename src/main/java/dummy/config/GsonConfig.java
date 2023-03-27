package dummy.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dummy.customserde.ArtistModelListDeser;
import dummy.customserde.LocalDateSerDe;
import dummy.instancecreator.ArtistModelListInstanceCreator;
import dummy.instancecreator.DeliveryDataInstanceCreator;
import dummy.instancecreator.DiscountInstanceCreator;
import dummy.instancecreator.LocalDateInstanceCreator;
import dummy.instancecreator.LocalDateTimeInstanceCreator;
import dummy.instancecreator.OrderInstanceCreator;
import dummy.instancecreator.OrderProductsInstanceCreator;
import dummy.instancecreator.ProductInstanceCreator;
import dummy.instancecreator.ShopInstanceCreator;
import dummy.instancecreator.UserInstanceCreator;
import dummy.model.Animal;
import dummy.model.ArtistModel;
import dummy.model.Cow;
import dummy.model.DeliveryData;
import dummy.model.Discount;
import dummy.model.Dog;
import dummy.model.Order;
import dummy.model.Product;
import dummy.model.Shop;
import dummy.model.User;


@EnableWebMvc
@Configuration
public class GsonConfig implements WebMvcConfigurer{
	
	@Value("${instancecreatorbeansexist}")
	String instcreatorsexist;
	
	@Bean
	public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {
		
		// this is used for SerDe with polymorphic objects
		RuntimeTypeAdapterFactory<Animal> adapter = RuntimeTypeAdapterFactory.of(Animal.class, "breed")
																		     .registerSubtype(Dog.class,Dog.class.getName())
																		     .registerSubtype(Cow.class,Cow.class.getName());
		
		GsonBuilder builder = new GsonBuilder();
		 customizers.forEach(c -> c.customize(builder)); // this is to customise configurations for GsonBuilder using application.properties file
				      builder
               //     .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeSerDe()) // use @JsonAdapter instead
					  .registerTypeHierarchyAdapter(LocalDate.class, new LocalDateSerDe())
					  .setObjectToNumberStrategy(new MyNumberStrategy())
					  .registerTypeAdapterFactory(adapter)
					  .registerTypeAdapter(new TypeToken<List<ArtistModel>>() {}.getType(), new ArtistModelListDeser()) 
					  .addSerializationExclusionStrategy(new ExcludeNullStrategy());
				if (this.instcreatorsexist.equals("yes")) {
					return instanceCreatorsConf(builder);
				}
				return builder;
	}
	
	/*
	 *  used as plugin for InstanceCreator<T> for populating custom parameterized constructor.
	 *  and InstanceCreator<T> is not allowed for the parameter for @JsonAdapter. You've to add registerTypeAdapter.
	 */
	@ConditionalOnProperty(name = "instancecreatorbeansexist",havingValue = "yes")
	@Bean
	public GsonBuilder instanceCreatorsConf(GsonBuilder gb) {
		return gb
			 .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeInstanceCreator())
			 .registerTypeAdapter(Discount.class, new DiscountInstanceCreator())
			 .registerTypeAdapter(DeliveryData.class, new DeliveryDataInstanceCreator())
			 .registerTypeAdapter(User.class, new UserInstanceCreator())
			 .registerTypeAdapter(Product.class, new ProductInstanceCreator())
			 .registerTypeAdapter(new TypeToken<List<Product>>() {}.getType(), new OrderProductsInstanceCreator())
			 .registerTypeAdapter(Shop.class, new ShopInstanceCreator())
			 .registerTypeAdapter(LocalDate.class, new LocalDateInstanceCreator())
			 .registerTypeAdapter(Order.class, new OrderInstanceCreator())
               .registerTypeAdapter(new TypeToken<List<ArtistModel>>() {}.getType(), new ArtistModelListInstanceCreator());
		
	}
	
	// it'll help to correctly format JSON response using REST APIs
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { 
	    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	    stringConverter.setWriteAcceptCharset(false);
	    stringConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
	    converters.add(stringConverter);
	    converters.add(new ByteArrayHttpMessageConverter());
	    converters.add(new SourceHttpMessageConverter<>());
	    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
	    gsonHttpMessageConverter.setGson(new GsonBuilder().create());
	    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
	    converters.add(gsonHttpMessageConverter);
	}
}
