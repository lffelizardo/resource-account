package br.com.mv.sistemas.app;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mv.sistemas.app.domain.User;
import br.com.mv.sistemas.app.dto.UserDTO;
import br.com.mv.sistemas.app.repository.UserRepository;
import br.com.mv.sistemas.app.resource.UserResource;
import br.com.mv.sistemas.app.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, WebAppContext.class})
@WebAppConfiguration
public class ResourceAccountApplicationTests {

	private MockMvc mockMvc;

	@MockBean
	private UserResource userResource;

	@MockBean
	private UserService service;

	@Autowired
    private WebApplicationContext applicationContext;

	@Autowired
	private UserRepository userRepository;

	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

	@Test
	public void getAllUsers() throws Exception {

		List<User> allUsers = Arrays.asList(
				new User(1L, "Teste", "teste", "teste@teste.com", "123456"),
				new User(2L, "Teste2", "teste2", "teste2@teste2.com", "654321"));

		userRepository.saveAll(allUsers);

		doReturn(new ResponseEntity<List<User>>(allUsers, HttpStatus.OK)).
		when(userResource).getUsers();
		mockMvc.perform(get("/user/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id",is(1)))
        .andExpect(jsonPath("$.[0].name", is("Teste")))
        .andExpect(jsonPath("$.[1].id",is(2)))
        .andExpect(jsonPath("$.[1].name", is("Teste2")));
		verify(userResource, times(1)).getUsers();
		verifyNoMoreInteractions(userResource);
	}

	@Test
	public void getUser() throws Exception {

		List<User> allUsers = Arrays.asList(
				new User(1L, "Teste", "teste", "teste@teste.com", "123456"));

		userRepository.saveAll(allUsers);

		doReturn(new ResponseEntity<List<User>>(allUsers, HttpStatus.OK)).
		when(userResource).getUsers();
		mockMvc.perform(get("/user/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id",is(1)))
        .andExpect(jsonPath("$.[0].name", is("Teste")));
		verify(userResource, times(1)).getUsers();
		verifyNoMoreInteractions(userResource);
	}

	@Test
	public void insertUser() throws Exception{

		UserDTO dto = new UserDTO("Teste", "teste", "teste@teste.com", "123456");

		when(userResource.insert(dto)).thenReturn(ResponseEntity.noContent().build());

		mockMvc.perform(
	            post("/users")
	                    .content(asJsonString(dto)))
	            .andExpect(status().isOk());
	    verifyNoMoreInteractions(userResource);
	}

	@Test
	public void update() throws Exception {
	    User user = new User(1L, "Teste2", "teste2", "teste@teste.com", "123456");
	    when(userResource.getUser(user.getId().toString())).thenReturn(new ResponseEntity<User>(user, HttpStatus.OK));
	    UserDTO dto = new UserDTO("Teste2", "teste2", "teste@teste.com", "123456");
	    when(userResource.update(dto.getId(),dto)).thenReturn(new ResponseEntity<User>(user, HttpStatus.OK));
	    mockMvc.perform(
	            put("/updateuser", dto)
	                    .content(asJsonString(dto)))
	            .andExpect(status().isOk());
	    verifyNoMoreInteractions(userResource);
	}

	@Test
	public void testDeletes() throws Exception {
		User user = new User(1L, "Teste2", "teste2", "teste@teste.com", "123456");
	    when(userResource.getUser(user.getId().toString())).thenReturn(new ResponseEntity<User>(user, HttpStatus.OK));
	    when(userResource.delete(user.getId().toString())).thenReturn(ResponseEntity.noContent().build());
	    mockMvc.perform(
	            delete("/deleteuser/{id}", user.getId().toString()))
	            .andExpect(status().isOk());
	    verifyNoMoreInteractions(userResource);
	}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
