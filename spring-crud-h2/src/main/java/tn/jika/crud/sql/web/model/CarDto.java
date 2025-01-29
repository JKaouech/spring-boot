package tn.jika.crud.sql.web.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.jika.crud.sql.model.CarStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

	private String id;

	private String registration; 
	
	private String model; 
	
    private Integer year; 
	
    private LocalDate circulation;
    
    private CarStatus status; 
    
}