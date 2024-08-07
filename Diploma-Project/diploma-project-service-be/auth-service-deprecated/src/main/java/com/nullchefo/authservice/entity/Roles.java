package com.nullchefo.authservice.entity;

// TODO make them relevant again
public enum Roles {

	ROLE_USER, ROLE_DEV, ROLE_ADMIN, ROLE_BI, ROLE_CALL_CENTER;

	public String getAuthority() {
		return name();
	}

}

