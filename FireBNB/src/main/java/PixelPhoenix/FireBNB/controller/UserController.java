package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.repository.HouseRepository;
import PixelPhoenix.FireBNB.repository.UserRepository;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.RatingService;
import PixelPhoenix.FireBNB.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService us;
	@Autowired
	private HouseService hs;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private HouseRepository hsrp;
	@Autowired
	private UserRepository usrp;

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/loggedIndex")
	public String loggedIndex() {
		return "adminIndex";
	}
	
	@GetMapping("/adminIndex")
	public String adminIndex() {
		return "adminIndex";
	}

	@RequestMapping(value = { "/usersList", "/usersList/search" })
	public String listUsers(Model model, 
			@RequestParam(name = "first_name", defaultValue = "") String first_name,
			@RequestParam(name = "last_name", defaultValue = "") String last_name,
			@RequestParam(name = "email", defaultValue = "") String email, Principal principal) {
		Iterable<User> listUsers = us.getUsers();

		String emailLoggedUser = principal.getName();
		User loggedUser = us.getUser(emailLoggedUser);
		Long id_loggedUser = loggedUser.getId_user();

		// Advanced Search
		// One param search
		if (!first_name.equals("") && last_name.equals("") && email.equals("")) {
			listUsers = usrp.findWithOne(first_name);
		}
		if (first_name.equals("") && !last_name.equals("") && email.equals("")) {
			listUsers = usrp.findWithOne(last_name);
		}
		if (first_name.equals("") && last_name.equals("") && !email.equals("")) {
			listUsers = usrp.findWithOne(email);
		}

		// 2 params search
		if (!first_name.equals("") && !last_name.equals("") && email.equals("")) {
			listUsers = usrp.findWithTwo(first_name, last_name);
		}
		if (first_name.equals("") && !last_name.equals("") && !email.equals("")) {
			listUsers = usrp.findWithTwo(email, last_name);
		}
		if (!first_name.equals("") && last_name.equals("") && !email.equals("")) {
			listUsers = usrp.findWithTwo(first_name, email);
		}

		// 3 params search
		if (!first_name.equals("") && !last_name.equals("") && !email.equals("")) {
			listUsers = usrp.findWithThree(first_name, last_name, email);
		}
		
		model.addAttribute("first_name", first_name);
		model.addAttribute("last_name", last_name);
		model.addAttribute("email", email);

		model.addAttribute("listUsers", listUsers);
		model.addAttribute("id_loggedUser", id_loggedUser);

		return "usersList";
	}

	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal, @RequestParam("email") String email) {
		
		User user = null;
		if(email != "") {
			 user = us.getUser(email);
		}else if(email == "") {
			String emailLoggedUser = principal.getName();
			 user = us.getUser(emailLoggedUser);		
		}

		List<String> listSenders = new ArrayList<>();
		Iterable<User> allUsers = us.getUsers();
		Iterable<Rating> listUserRatings = ratingService.getRatingsByReceiver(us.getIdByEmail(user.getEmail()));
		Hashtable<Long,String> listRateNames = new Hashtable<Long, String>();
		Hashtable<Long,String> listRatePhotos = new Hashtable<Long, String>();
		Hashtable<Long,String> listRateEmails = new Hashtable<Long, String>();
		
		model.addAttribute("listUserRatings", listUserRatings);
		double ratingH = 0.0;

		int size = 0;
		for (Rating rating : listUserRatings) {
			ratingH += rating.getValue();
			size += 1;
			
			Optional<User> u = us.getUserId(rating.getId_userSender());
			User userSender = u.get();
			listRateNames.put(rating.getId_userSender(), userSender.getFirstName() + ' ' + userSender.getLastName());
			listRatePhotos.put(rating.getId_userSender(), userSender.getProfilePicture());
			listRateEmails.put(rating.getId_userSender(), userSender.getEmail());

		}

		ratingH = ratingH / size;
		ratingH = (double) Math.round(ratingH * 100) / 100;
		user.setRating(ratingH);
		user.setRatingSize(size);
		us.updateUser(user);

		int numberOfHouses = hs.numberHouses(user.getId_user());

		model.addAttribute("listSenders", listSenders);
		model.addAttribute("numberOfHouses", numberOfHouses);
		model.addAttribute("user", user);
		model.addAttribute("principal", principal);
		model.addAttribute("listRateNames", listRateNames);
		model.addAttribute("listRateEmails", listRateEmails);
		model.addAttribute("listRatePhotos", listRatePhotos);
		return "userProfile";
	}

	@GetMapping("/login")
	public String registrationForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "register";
	}

	@PostMapping("/userRegistration")
	public String processRegister(@ModelAttribute User user, Model model) {

		if (us.isUserExist(user.getEmail())) {
			model.addAttribute("exist", true);
		} else {
			us.createUser(user);
			model.addAttribute("registrationSuccess", true);
		}

		return "register";
	}

	@GetMapping("/profile/update")
	public String editUser(Model model, Principal principal, 
			@RequestParam("email") String email,
			@RequestParam("type") String type) {
		if(type.equals("myProfile")) {
			String emailUser = principal.getName();
			User user = us.getUser(emailUser);
			model.addAttribute("user", user);
		}else if(type.equals("userProfile")) {
			User user = us.getUser(email);
			model.addAttribute("user", user);
		}	
		return "userEdit";
	}

	@PostMapping("/profile/update")
	public String updateUser(@RequestParam("email") String email, Model model,
			@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName,
			@RequestParam("address") String address, 
			@RequestParam("city") String city,
			@RequestParam("postalCode") int postalCode, 
			@RequestParam("country") String country,
			@RequestParam("description") String description,
			@RequestParam("additionalAddress") String additionalAddress, 
			@RequestParam("phoneNumber") int phoneNumber,
			@RequestParam("password") String password, 
			@RequestParam(name = "edit", defaultValue = "") int edit,
			Principal principal){
		if (edit == 0) {
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("address", address);
			model.addAttribute("city", city);
			model.addAttribute("postalCode", postalCode);
			model.addAttribute("country", country);
			model.addAttribute("additionalAddress", additionalAddress);
			model.addAttribute("phoneNumber", phoneNumber);
			model.addAttribute("description",description);
			return "userEdit";
		}
		User user = us.getUser(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setCity(city);
		user.setPostalCode(postalCode);
		user.setCountry(country);
		user.setAdditionalAddress(additionalAddress);
		user.setPhoneNumber(phoneNumber);
		user.setDescription(description);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		us.updateUser(user);
		
		if(principal.getName() == user.getEmail()) {
			return "redirect:/profile(email='')";
		}
		return "redirect:/profile(email="+user.getEmail()+")";
		
	}

	@RequestMapping(value = "/profile/delete")
	public String delete(Model model, @RequestParam(name = "email") String email) {
		User user = us.getUser(email);
		Long id_user = user.getId_user();
		// us.deleteUser(user);
		us.deleteUser(id_user);
		return "redirect:/usersList";
	}

}
