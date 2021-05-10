package me.soyeong;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.soyeong.common.TestDescription;
import me.soyeong.event.Event;
import me.soyeong.event.EventDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
public class EventControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//MebMvcTest는 repository 용 빈은 별도 등록을 하지 않음
	//@MockBean
	//EventRepository eventRepository; 
	
	@Test
	@TestDescription("정상적인 이벤트 생성하는 테스트")
	public void createEvent() throws Exception{
		EventDto event = EventDto.builder()
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
		//event.setId(20);
		//Mockito.when(eventRepository.save(event)).thenReturn(event); //저장하려는 event가 내 event 와 같으면 event를 return
		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
		.andDo(print()) //요청 응답 ㅘㄱ인 가능
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id").exists())
		.andExpect(header().exists(HttpHeaders.LOCATION))
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
		//입력 값 제한
		.andExpect(jsonPath("free").value(false))
		.andExpect(jsonPath("offline").value(true))
		.andExpect(jsonPath("_links.self").exists())
		.andExpect(jsonPath("_links.query-events").exists())
		.andExpect(jsonPath("_links.update-events").exists());
		
		 
	}
	@Test
	@TestDescription("입력 받을 수 없는 값을 사용하는 경우 에러가 발생하는 테스트")
	public void createEvent_Bad_Request() throws Exception{
		Event event = Event.builder()
				.id(100)
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
				.free(true)
				.offline(false)
				.build();
		//event.setId(20);
		//Mockito.when(eventRepository.save(event)).thenReturn(event); //저장하려는 event가 내 event 와 같으면 event를 return
		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
				.andDo(print()) //요청 응답 ㅘㄱ인 가능
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@TestDescription("입력 값이 비어있는 경우 에러가 발생하는 테스트")
	public void createEvent_Bad_Request_Empty_Input() throws Exception{
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
			.andExpect(status().isBadRequest()); 
	}
	@Test
	@TestDescription("입력값이 잘못된 경우에 에러가 발생하는 테스트")
	public void createEvent_Bad_Request_Wrong_Input() throws Exception{
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("nice weather")
				.beginEnrollmentDateTime(LocalDateTime.of(2020,1 ,4,14,16))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 1,3,14,15))
				.beginEventDateTime(LocalDateTime.of(2020, 1,5,15,10))
				.endEventDateTime(LocalDateTime.of(2020, 1,3,14,15))
				.basePrice(100000)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역")
				.build();
		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$[0].objectName").exists())
			.andExpect(jsonPath("$[0].field").exists())
			.andExpect(jsonPath("$[0].defaultMessage").exists())
			.andExpect(jsonPath("$[0].code").exists());
	}
}
