package com.user.details;

import com.enums.Role;

public class Admin extends User{

	public Admin() {
	}

	public Admin(String name, String email, String phone, String password,String address) {
		super(name,password,email,phone,address);
	}
	public void setRole(User user, Role role) {
		user.role = role;
	}
	
}
