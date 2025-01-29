package tn.jika.crud.sql.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String id;
	
	private String name;
	
	private String email;
	
	private List<RoleDto> roles;
}
