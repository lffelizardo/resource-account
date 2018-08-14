package br.com.mv.sistemas.app.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mv.sistemas.app.domain.User;
import br.com.mv.sistemas.app.dto.UserDTO;
import br.com.mv.sistemas.app.repository.UserRepository;
import br.com.mv.sistemas.app.resource.exception.FieldMessage;
import br.com.mv.sistemas.app.specification.UserSpecification;
import br.com.mv.sistemas.app.specification.criteria.SearchCriteria;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserDTO> {

	@Autowired
	private UserRepository userRepo;

	@Override
	public void initialize(UserInsert ann) {}

	@Override
	public boolean isValid(UserDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		UserSpecification spec = new UserSpecification(new SearchCriteria("email", ":", objDto.getEmail()));

	    List<User> results = userRepo.findAll(spec);


		if (results != null && !results.isEmpty()) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
