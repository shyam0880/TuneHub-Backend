package com.example.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.main.entity.Users;
import com.example.main.services.UsersService;

@Controller
public class UserController {
	@Autowired
	UsersService srv;
	
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user)
	{
		boolean userStatus = srv.emailExists(user.getEmail());
		if(userStatus==false)
		{
			srv.addUser(user);
			System.out.println("user added");
		}
		else
		{
			System.out.println("User Already exists");
		}
		return "home";
		
	}

}
