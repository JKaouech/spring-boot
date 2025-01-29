package tn.jika.crud.sql.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDto {

	private String id;
	
	private String name;
	
	private String email;
	
}
