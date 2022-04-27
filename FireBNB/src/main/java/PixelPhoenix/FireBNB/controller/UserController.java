package PixelPhoenix.FireBNB.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.service.UserService;

@Controller
public class UserController {

	@Autowired
	//private User person = new User(0,"Lina","Yanouri",20,4,"lily.lily@lily.com", "lily");
	private UserService us;
	
	@GetMapping("/userProfile/{id}")
	public String userProfile(Model model, @PathVariable long id) {
		Optional<User> person = us.getUser(id);
		model.addAttribute("person",person);
		return "userProfile";
		
	}
}
