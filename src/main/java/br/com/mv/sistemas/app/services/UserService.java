package br.com.mv.sistemas.app.services;

import java.util.List;

import br.com.mv.sistemas.app.domain.User;
import br.com.mv.sistemas.app.dto.UserDTO;

public interface UserService {

	List<User> findAll();

	User findUser(String id);

	User fromDTO(UserDTO objDto);

	User insert(User obj);

	User update(User obj);

	void delete(User obj);


}
