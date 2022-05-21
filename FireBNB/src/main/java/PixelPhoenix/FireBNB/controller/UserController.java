package PixelPhoenix.FireBNB.controller;

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
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		Iterable<User> listUsers = us.getUsers();
		model.addAttribute("listUsers", listUsers);
		
		return "usersList";
	}
	
	@GetMapping("/userProfile/{id}")
	public String userProfile(Model model, @PathVariable long id) {
		//Optional<User> person = us.getUser(id);
		//model.addAttribute("person", person);
		us.getUser(id).ifPresent(person -> model.addAttribute("person", person));
		return "userProfile";
	}
	
	@GetMapping("/registration")
	public String registrationForm(Model model) {
		User user = new User();
	    model.addAttribute("user",user);
	    
	    return "register";
	}
	
	@PostMapping("/userRegistration")
	public String processRegister(@ModelAttribute User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		us.createUser(user);
		
		return "registrationSuccess";
	}
	
	@GetMapping("/houseProfile")
	public String houseProfile() {
		return "houseProfile";
	}

	@GetMapping("/userProfile")
	public String userProfile() {
		return "userProfile";
	}
	
	@GetMapping("/header")
	public String header() {
		return "header";
	}
}
