package me.soyeong.event;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor 
public class EventDto {
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@NotNull
	private LocalDateTime beginEnrollmentDateTime;
	@NotNull
	private LocalDateTime closeEnrollmentDateTime;
	@NotNull
	private LocalDateTime beginEventDateTime;
	@NotNull
	private LocalDateTime endEventDateTime;
	private String location; //optional, 이게 없으면 온라인 모임
	@Min(0)
	private int basePrice;
	@Min(0)
	private int maxPrice;
	@Min(0) 
	private int limitOfEnrollment;
}