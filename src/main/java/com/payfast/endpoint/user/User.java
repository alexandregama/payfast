package com.payfast.endpoint.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private Long id;

	private String name;

	@Deprecated //JAX-B eyes only
	public User() {
	}
	
	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public User(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
