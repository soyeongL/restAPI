package me.soyeong.event;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {
	@Id @GeneratedValue
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
	@Enumerated(EnumType.STRING)
	private EvenStatus eventStatus = EvenStatus.DRAFT;
	
	public void update() {
		this.free = this.basePrice == 0 && this.maxPrice ==0? true: false;
		this.offline = this.location==null || this.location.trim().isEmpty() ? false: true; 
	}
}
