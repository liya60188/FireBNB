package PixelPhoenix.FireBNB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import PixelPhoenix.FireBNB.model.User;

@Controller
public class UserController {

	private User person = new User(0,"Lina","Yanouri",20,4,"lily.lily@lily.com", "lily");
	
	@GetMapping("/userProfile")
	public String userProfile(Model model) {
		model.addAttribute("person",person);
		return "userProfile";
		
	}
}
