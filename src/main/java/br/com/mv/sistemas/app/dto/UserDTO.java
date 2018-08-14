package br.com.mv.sistemas.app.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import br.com.mv.sistemas.app.services.validation.UserInsert;

@UserInsert
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 5535446474350342396L;

	private String id;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caracteres")
	private String name;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caracteres")
	private String login;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=6, max=10, message="O tamanho deve ser entre 6 e 10 caracteres")
	private String password;

	private String passwordConfirm;

	private boolean policy;

	public UserDTO() {}

	public UserDTO(String name, String login, String email, String password){
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean getPolicy() {
		return policy;
	}

	public void setPolicy(boolean policy) {
		this.policy = policy;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdAsLong() {
		if(StringUtils.hasText(this.id)){
			return new Long(this.id);
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
