package com.kodnest.tunehub.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kodnest.tunehub.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Integer>
{
	   AppUser findByEmail(String email);
	   AppUser findByUsername(String username);
}