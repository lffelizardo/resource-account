package br.com.mv.sistemas.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.sistemas.app.domain.User;
import br.com.mv.sistemas.app.dto.UserDTO;
import br.com.mv.sistemas.app.repository.UserRepository;
import br.com.mv.sistemas.app.security.UserSS;
import br.com.mv.sistemas.app.services.UserService;
import br.com.mv.sistemas.app.specification.UserSpecification;
import br.com.mv.sistemas.app.specification.criteria.SearchCriteria;

@Service
public class UserServiceImpl implements UserService, UserDetailsService  {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${jwt.secret.pwd}")
	private String secret;

	@Override
	public List<User> findAll(){
		return userRepo.findAll();
	}

	@Override
	public User findUser(String id){
		UserSpecification spec = new UserSpecification(new SearchCriteria("id", ":", id));

		List<User> users = userRepo.findAll(spec);
		if(users != null && !users.isEmpty()){
			return users.get(0);
		}

		return null;
	}

	@Override
	public User fromDTO(UserDTO objDto) {
		User user = new User();
		user.setId(objDto.getIdAsLong());
		user.setName(objDto.getName());
		user.setLogin(objDto.getLogin());
		user.setPassword( bCryptPasswordEncoder.encode( objDto.getPassword() ) );
		user.setEmail(objDto.getEmail());
		return user;
	}

	@Override
	@Transactional
	public User insert(User obj) {
		obj = userRepo.save(obj);
		return obj;
	}

	@Override
	@Transactional
	public User update(User obj) {
		obj = userRepo.save(obj);
		return obj;
	}

	@Override
	@Transactional
	public void delete(User obj) {
		userRepo.delete(obj);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String [] token = username.split(";");
		UserSpecification spec = new UserSpecification(new SearchCriteria("email", ":", token[2]));

		List<User> users = userRepo.findAll(spec);
		if(users != null && !users.isEmpty()){
			User user = users.get(0);
			return new UserSS(user.getId(), user.getName(), user.getLogin(), user.getEmail(), user.getPassword());

		}else{
			throw new UsernameNotFoundException(username);

		}
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

}
