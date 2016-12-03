package com.mikhailau.training.motordepot.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Credentials extends AbstractModel {

	@Column(updatable = false,unique=true)
	private String login;

	@Column
	private String password;

	@Column
	@Enumerated(value = EnumType.ORDINAL)
	private Role role;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Entry [login=" + login + ", password=" + password + ", role=" + role + "]";
	}
}
