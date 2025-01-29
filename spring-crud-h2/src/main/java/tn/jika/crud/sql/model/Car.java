package tn.jika.crud.sql.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR")
public class Car {

	@Id
	private String id;
	
	private String registration; // Vehicle Identification Number
	
	private String model; // Car's model
	
    private Integer year; // Year of manufacture
	
    private LocalDate circulation; // put into circulation

    @Enumerated(EnumType.STRING)
    private CarStatus status;  // Car's condition
    
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
}
