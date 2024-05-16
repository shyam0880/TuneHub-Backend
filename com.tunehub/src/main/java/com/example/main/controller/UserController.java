package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.main.entity.Song;
import com.example.main.entity.Users;
import com.example.main.services.SongService;
import com.example.main.services.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
	@Autowired
	UsersService srv;
	
	@Autowired
	SongService songService;
	
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
	public String validate(@RequestParam String email,String password,HttpSession session, Model model)
	{
		if(srv.validateUser(email,password)== true)
		{
			String role = srv.getRole(email);
			
			//Creating session
			session.setAttribute("email", email);
			
			if(role.equals("admin"))
			{
				return "adminhome";
			}
			else
			{
				Users user = srv.getUser(email);
				boolean userStatus = user.isPremium();
				model.addAttribute("isPremium", userStatus);
				
				//for song view
				List <Song> songList = songService.fetchAllSongs();
				model.addAttribute("songs", songList);
				return "customerhome";				
			}
		}
		else
		{
			return "login";
		}
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	

}
