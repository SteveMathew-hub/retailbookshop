package com.retail.retailbookshop.dto;

import javax.validation.constraints.Pattern;

public class RoleDTO {

	private String id;
	@Pattern(regexp = "(ADMIN|CUSTOMER)", message = "{role.invalid}")
	private String role;

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

}
