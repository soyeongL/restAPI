package me.soyeong.event;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	public void validate(EventDto eventDto, Errors errors) {
		if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() !=0) {
			errors.rejectValue("basePrice", "wrongValue","BasePirce is wrong");
			errors.rejectValue("maxPrice", "wrongValue","MaxPirce is wrong");
		}
		
		LocalDateTime endEventTime = eventDto.getEndEventDateTime();
		if(endEventTime.isBefore(eventDto.getBeginEventDateTime()) || 
				endEventTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
				endEventTime.isBefore(eventDto.getEndEventDateTime())) {
			errors.rejectValue("endEventDateTime", "wrong value", "EndEventDateTime is worng");
		}
		
		//Todo..
	}
}
