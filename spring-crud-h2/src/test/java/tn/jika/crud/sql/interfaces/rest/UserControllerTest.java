package tn.jika.crud.sql.interfaces.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tn.jika.crud.sql.JsonUtil;
import tn.jika.crud.sql.model.User;
import tn.jika.crud.sql.service.UserService;
import tn.jika.crud.sql.web.mapper.RoleMapper;
import tn.jika.crud.sql.web.mapper.UserMapper;
import tn.jika.crud.sql.web.rest.UserController;

@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private UserMapper userMapper;
    
    @MockBean
    private RoleMapper roleMapper;

    @MockBean
    private UserService userService;

	private String id;
	private String name;
	private String email;

	@BeforeAll
	public void setUp() {
		id = "id-01";
		name = "JIKA";
		email = "jika@test.com";
	}

    @Test
    public void testAddUser()
      throws Exception {
        
    	User userToSave = User.builder().name(name).email(email).build();
    	
    	User user =  User.builder().id(id).name(name).email(email).build();
		when(userService.addUser(any())).thenReturn(user);
		
        mvc.perform(post("/api/v1/users")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JsonUtil.toJson(userToSave)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.name", is(name)));

//		verify(userService, times(1)).addUser(userToSave);
    }
    
}
