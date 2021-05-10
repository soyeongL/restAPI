package me.soyeong.event;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//BeanSerializer 가 json으로 변환할때 field 명을 사용 
public class EventResource extends EntityModel<Event>{
	public EventResource(Event content, Link... links) {
		super(content, links);
		add(linkTo(EventController.class).slash(content.getId()).withSelfRel()); 
	}
}
