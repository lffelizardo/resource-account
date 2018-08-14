package br.com.mv.sistemas.app.resource;


import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mv.sistemas.app.domain.User;
import br.com.mv.sistemas.app.dto.UserDTO;
import br.com.mv.sistemas.app.services.UserService;


@RestController
@RequestMapping(value="/user")
public class UserResource {

	@Autowired
	private UserService service;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers(){

		List<User> users = service.findAll();
		if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") String id){
		User user = service.findUser(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/newuser", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UserDTO objDto) {
		User user = service.fromDTO(objDto);
		user = service.insert(user);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable("id") String id, @Valid @RequestBody UserDTO objDto) {
		User user = service.fromDTO(objDto);
		user = service.insert(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		User user = service.findUser(id);
		if (user == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

		service.delete(user);
		return ResponseEntity.noContent().build();
	}

}
