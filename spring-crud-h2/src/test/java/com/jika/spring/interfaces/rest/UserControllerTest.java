package com.jika.spring.interfaces.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jika.spring.JsonUtil;
import com.jika.spring.model.User;
import com.jika.spring.service.UserService;

@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    
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
        
    	User userToSave = User.builder().id(id).name(name).email(email).build();
		when(userService.addUser(any())).thenReturn(userToSave);
		
        mvc.perform(post("/api/users")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JsonUtil.toJson(userToSave)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.name", is(name)));

		verify(userService, times(1)).addUser(userToSave);
    }
    
}
