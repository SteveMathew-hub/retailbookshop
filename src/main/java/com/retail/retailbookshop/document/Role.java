package com.retail.retailbookshop.document;

import java.util.UUID;

public class Role {

	private String id;

	private String role;

	public Role() {
		this.id = idGenerator();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String idGenerator() {
		UUID uuid = UUID.randomUUID();
		return "role_" + uuid.hashCode();
	}

}
