package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/users")
	public String listUsers(Model model, @RequestParam(defaultValue="") String name) {
		Iterable<User> listUsers = us.getUsers();
		model.addAttribute("listUsers", listUsers);
		//model.addAttribute("users", us.findByName(name));
		
		return "usersList";
	}
	
	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal/*@PathVariable long id*/) {
		
		String email = principal.getName();
		User user = us.getUser(email);
		model.addAttribute("user", user);
		//us.getUser(email).ifPresent(person -> model.addAttribute("person", person));
		return "userProfile";
	}
	
	@GetMapping("/register")
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
	
//	@RequestMapping(value = "/profile/update")
//	public String edit(Model model, @RequestParam(name = "email", defaultValue = "") String email,
//			@RequestParam(name = "edit", defaultValue = "") String edit) {
//			User user = us.getUser(email);
//		if (edit.equals("0")) {
//			model.addAttribute("user", user);
//			return "userEdit";
//		} else {
//			us.updateUser(user);
//
//			return "redirect:/users";
//		}
//	}
	@GetMapping("/profile/update/{email}")
	public String editUser(Model model, @PathVariable(name = "email") String email){
		User user = us.getUser(email);
		model.addAttribute("user",user);
		return "userEdit";
	}
	@PostMapping("/profile/update")
	public String updateUser(@RequestParam("email") String email, Model model,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("age") int age,
			@RequestParam("address") String address,
			@RequestParam("city") String city,
			@RequestParam("postalCode") int postalCode,
			@RequestParam("country") String country,
			@RequestParam("additionalAddress") String additionalAddress,
			@RequestParam("phoneNumber") int phoneNumber,
			@RequestParam(name = "edit", defaultValue = "") int edit
			) {
		if(edit == 0) {
		model.addAttribute("firstName", firstName);
		model.addAttribute("lastName", lastName);
		model.addAttribute("age", age);
		model.addAttribute("address", address);
		model.addAttribute("city", city);
		model.addAttribute("postalCode", postalCode);
		model.addAttribute("country", country);
		model.addAttribute("additionalAddress", additionalAddress);
		model.addAttribute("phoneNumber", phoneNumber);
		return "userEdit";
		}
		User user = us.getUser(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAge(age);
		user.setAddress(address);
		user.setCity(city);
		user.setPostalCode(postalCode);
		user.setCountry(country);
		user.setAdditionalAddress(additionalAddress);
		user.setPhoneNumber(phoneNumber);
		us.updateUser(user);   

		return "redirect:/users";
	}
	
	@RequestMapping(value = "/profile/delete")
	public String delete(Model model, @RequestParam(name = "email") String email) {
		User user = us.getUser(email);
		us.deleteUser(user);
		return "redirect:/users";
	}


}
