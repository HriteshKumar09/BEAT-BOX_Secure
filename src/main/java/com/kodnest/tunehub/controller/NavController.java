package com.kodnest.tunehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController 
{
@GetMapping("/login")
public String login()
	{
	return "login";
	}

@GetMapping("/registration")
public String registration()
	{
	return "registration";
	}

@GetMapping("/addsong")
public String song()
	{
	return "song";
	}

@GetMapping("/")
public String index() {
    return "index"; // Returns the index page
}
@GetMapping("/addsongs")
	public String newsong()
	{
		return "Song";

	}
@GetMapping("/about")
	public String aboutPage() {
		return "about"; // This will return the about.html template
	}
	

	@GetMapping("/contact")
	public String contactPage() {
	    return "contact"; // This will return the contact.html template
	}

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
   
    @GetMapping("navlogout")
	public String logout()
	{
		return "home";
	}
}


