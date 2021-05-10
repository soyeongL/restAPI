package me.soyeong;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import me.soyeong.event.Event;

@RunWith(JUnitParamsRunner.class)
public class EventTest {
	@Test
	public void build() {
		Event event = Event.builder()
				.name("event title")
				.description("REST API DEMO")
				.build();
		assertThat(event).isNotNull();
	}
	
	@Test
	public void javaBean() {
		//Given
		String name = "Event";
		String des = "Spring";
		
		//When
		Event event = new Event();
		event.setName(name);
		event.setDescription("Spring");
		
		//Then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(des);
	}
	
	@Test
	@Parameters
	public void testFree(int basePrice, int maxPrice, boolean isFree) {
		//Given
		Event event = Event.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();
		
		//when
		event.update();
		
		//then
		assertThat(event.isFree()).isEqualTo(isFree);
	}
	
	//prefix 를 parametesFor 를 작성하면 알아서 매핑해줌(컨벤션)
	//@Parameters(method="paramsForTestFree") 이렇게 해줘도 됨
	private Object[] parametersForTestFree() {
		return new Object[] {
			new Object[] {0, 0, true},
			new Object[] {0, 100, false},
			new Object[] {100,0, false},
			new Object[] {100, 200, false}
		};
	}
	
	
	
	
	
	
	@Test
	@Parameters
	public void testOffline(String location, boolean isOffline) {
		//Given
		Event event = Event.builder()
				.location(location)
				.build();
		
		//when
		event.update();
		
		//then
		assertThat(event.isOffline()).isEqualTo(isOffline);

	}
	private Object[] parametersForTestOffline() {
		return new Object[] {
			new Object[] {"강남", true},
			new Object[] {null, false},
			new Object[] {"", false}
		};
	}
}
