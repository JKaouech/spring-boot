package tn.jika.crud.sql.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.web.model.SimpleUserDto;
import tn.jika.crud.sql.web.model.UserDto;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

	UserDto userToDto (User user);
	List<UserDto> userToDto (List<User> user);
	
	SimpleUserDto userToSimpleDto (User user);
	List<SimpleUserDto> userToSimpleDto (List<User> user);
	
	User dtoToUser (UserDto user);
	User dtoToUser (SimpleUserDto user);

	List<User> dtoToUser (List<UserDto> user);
}
