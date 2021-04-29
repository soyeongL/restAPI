package me.soyeong.event;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/events", produces=MediaTypes.HAL_JSON_VALUE)
public class EventController {
	@Autowired
	EventRepository eventRepository;
	//생성자를 통한 주입도 가능
	/*
	 * private final EventRepository eventRepository;
	 * public EventController(EventRepository eventRepository){
	 * 	this.eventRepository = eventRepository;
	 * }
	 * */
	
	@PostMapping
	public ResponseEntity createEvent(@RequestBody Event event) {
		Event newEvent = this.eventRepository.save(event);
		URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
		//event.setId(10);
		//link
		return ResponseEntity.created(createdUri).body(event );
	}
}