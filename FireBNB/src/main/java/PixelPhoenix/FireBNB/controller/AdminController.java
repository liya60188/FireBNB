package PixelPhoenix.FireBNB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService us;
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		Iterable<User> listUsers = us.getUsers();
		model.addAttribute("listUsers", listUsers);
		
		return "usersList";
	}
}
