package me.soyeong.event;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	public void validate(EventDto eventDto, Errors errors) {
		if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() !=0) {
			errors.reject("wrongPrices", "Value of prices "); //global error
		}
		
		LocalDateTime endEventTime = eventDto.getEndEventDateTime();
		if(endEventTime.isBefore(eventDto.getBeginEventDateTime()) || 
				endEventTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
				endEventTime.isBefore(eventDto.getEndEventDateTime())) {
			errors.rejectValue("endEventDateTime", "wrong value", "EndEventDateTime is worng"); //field error
		}
		
		//Todo..
	}
}
