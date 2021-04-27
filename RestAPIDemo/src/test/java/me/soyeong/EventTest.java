package me.soyeong;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import me.soyeong.event.Event;

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
}
