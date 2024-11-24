package com.kodnest.tunehub.service;

import com.kodnest.tunehub.entity.AppUser;

public interface UserService 

{
public void saveUser(AppUser user);

public boolean emailExists(AppUser user);

public boolean validUser(String email, String password);

public String getRole(String email);

public static AppUser getUser(String mail) {
	
	return null;
}

public void updateUser(AppUser user);
}