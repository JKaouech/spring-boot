package tn.jika.crud.sql.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.jika.crud.sql.model.Sex;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String id;
	
	private String name;

	private String firstName;

	private String lastName;

	private String adress;

	private Sex sex;
	
	private String email;
	
	private List<CarDto> cars;
	



	
}
