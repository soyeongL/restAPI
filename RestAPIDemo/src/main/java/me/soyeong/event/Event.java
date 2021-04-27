package me.soyeong.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Event {
	
	private Integer id;
	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private LocalDateTime closeEnrollmentDateTime;
	private LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String location; //optional, 이게 없으면 온라인 모임
	private int basePrice;
	private int maxPrice;
	private int limitOfEnrollment;
	private boolean offline;
	private boolean free; 
	private EvenStatus eventStatus;
}
