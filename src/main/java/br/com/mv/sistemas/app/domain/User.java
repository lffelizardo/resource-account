package br.com.mv.sistemas.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotEmpty
	@Column(name="name", length=20, insertable=true, nullable=false)
    private String name;

	@NotEmpty
	@Column(name="login", length=20, insertable=true, nullable=false)
    private String login;

	@NotEmpty
	@Column(name="email", unique=true, length=100, insertable=true, nullable=false)
    private String email;

	@JsonIgnore
	@NotEmpty
	@Column(name="password", length=100, insertable=true, nullable=true)
    private String password;

	public User(){

	}



	public User(Long id, @NotEmpty String name, @NotEmpty String login, @NotEmpty String email,
			@NotEmpty String password) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.password = password;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
}
