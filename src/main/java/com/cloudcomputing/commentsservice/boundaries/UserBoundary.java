package com.cloudcomputing.commentsservice.boundaries;

public class UserBoundary {

	private String email;
	private String[] roles;

	public UserBoundary() {
		// TODO Auto-generated constructor stub
	}

	public UserBoundary(String email, String[] roles) {
		super();
		this.email = email;
		this.roles = roles;
	}


	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
