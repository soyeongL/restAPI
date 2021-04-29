package me.soyeong;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.soyeong.event.Event;
import me.soyeong.event.EventRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//MebMvcTest는 repository 용 빈은 별도 등록을 하지 않음
	@MockBean
	EventRepository eventRepository; 
	
	@Test
	public void createEvent() throws Exception{
		Event event = Event.builder()
				.name("Spring")
				.description("nice weather")
				.beginEnrollmentDateTime(LocalDateTime.of(2020,1,1,14,16))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 1,3,14,15))
				.beginEventDateTime(LocalDateTime.of(2020, 1,1,15,10))
				.endEventDateTime(LocalDateTime.of(2020, 1,3,14,15))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역")
				.build();
		event.setId(20);
		Mockito.when(eventRepository.save(event)).thenReturn(event);
		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
		.andDo(print()) //요청 응답 ㅘㄱ인 가능
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id").exists())
		.andExpect(header().exists(HttpHeaders.LOCATION))
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
	}
}