package com.example.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@PostMapping("/validate")
	//public String validate(@RequestParam("email") String email,@RequestParam("password") String password) we can use this also
	public String validate(@RequestParam String email,String password)
	{
		if(srv.validateUser(email,password)== true)
		{
			String role = srv.getRole(email);
			if(role.equals("admin"))
			{
				return "adminhome";
			}
			else
			{
				return "customerhome";				
			}
		}
		else
		{
			return "login";
		}
	}

}
