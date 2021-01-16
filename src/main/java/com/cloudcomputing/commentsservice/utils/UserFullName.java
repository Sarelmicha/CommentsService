package com.cloudcomputing.commentsservice.utils;

public class UserFullName {
	private String first;
	private String last;
	
	public UserFullName() {
		// TODO Auto-generated constructor stub
	}
	
	public UserFullName(String first, String last) {
		// TODO Auto-generated constructor stub
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

}
