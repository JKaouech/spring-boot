package tn.jika.crud.sql.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import tn.jika.crud.sql.model.Role;
import tn.jika.crud.sql.web.model.RoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	RoleDto roleToDto (Role role);
	List<RoleDto> roleToDto (List<Role> roles);
	
	Role dtoToRole (RoleDto role);
	List<Role> dtoToRole (List<RoleDto> roles);
}
