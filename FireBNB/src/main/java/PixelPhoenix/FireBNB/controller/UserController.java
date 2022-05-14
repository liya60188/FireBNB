package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.repository.UserRepository;
import PixelPhoenix.FireBNB.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService us;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal/*@PathVariable long id*/) {
		
		String email = principal.getName();
		User user = us.getUser(email);
		model.addAttribute("person", user);
		//us.getUser(email).ifPresent(person -> model.addAttribute("person", person));
		return "userProfile";
	}
	
	@GetMapping("/registration")
	public String registrationForm(Model model) {
		User user = new User();
	    model.addAttribute("user",user);
	    
	    return "register";
	}
	
	@PostMapping("/userRegistration")
	public String processRegister(@ModelAttribute User user, Model model) {
		
		if(us.isUserExist(user.getEmail())) {
			model.addAttribute("exist",true);
			return "register";
		}
		us.createUser(user);
		
		return "registrationSuccess";
	}
	
	@GetMapping("/houseProfile")
	public String houseProfile() {
		return "houseProfile";
	}


}
