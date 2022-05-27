package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.util.ArrayList;
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

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.User;
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

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/admin/users")
	public String listUsersAdmin(Model model, @RequestParam(defaultValue = "") String name) {
		Iterable<User> listUsers = us.getUsers();
		model.addAttribute("listUsers", listUsers);
		// model.addAttribute("users", us.findByName(name));

		return "usersList";
	}

	@GetMapping("/usersList")
	public String listUsers(Model model, @RequestParam(defaultValue = "") String name) {
		Iterable<User> listUsers = us.getUsers();
		model.addAttribute("listUsers", listUsers);
		// model.addAttribute("users", us.findByName(name));

		return "usersList";
	}

	@GetMapping("/profile/{email}")
	public String userProfile(Model model, @PathVariable("email") String email) {
		// CHANGED BY AMANDA - Show all Ratings + Average
		User user = us.getUser(email);
		
		List<String> listSenders = new ArrayList<>();
		Iterable<User> allUsers = us.getUsers();
		Iterable<Rating> listUserRatings = ratingService.getRatingsByReceiver(us.getIdByEmail(email));
		model.addAttribute("listUserRatings", listUserRatings);
		double ratingH = 0.0;

		int size = 0;
		for (Rating rating : listUserRatings) {
			ratingH += rating.getValue();
			size += 1;
			for (User sender : allUsers) {
				if(sender.getId_user() == rating.getId_userSender()) {
					listSenders.add(sender.getFirstName() + " " + sender.getLastName());
				}
			}
		}

		ratingH = ratingH / size;
		ratingH = (double) Math.round(ratingH * 100) / 100;
		user.setRating(ratingH);
		us.updateUser(user);

		int numberOfHouses = hs.numberHouses(user.getId_user());

		model.addAttribute("listSenders", listSenders);
		model.addAttribute("user", user);
		model.addAttribute("numberOfHouses", numberOfHouses);
		return "userProfile";
	}

	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal) {

		String emailLoggedUser = principal.getName();
		User user = us.getUser(emailLoggedUser);
		
		//chaananananged am
		List<String> listSenders = new ArrayList<>();
		Iterable<User> allUsers = us.getUsers();
		Iterable<Rating> listUserRatings = ratingService.getRatingsByReceiver(us.getIdByEmail(user.getEmail()));
		model.addAttribute("listUserRatings", listUserRatings);
		double ratingH = 0.0;

		int size = 0;
		for (Rating rating : listUserRatings) {
			ratingH += rating.getValue();
			size += 1;
			for (User sender : allUsers) {
				if(sender.getId_user() == rating.getId_userSender()) {
					listSenders.add(sender.getFirstName() + " " + sender.getLastName());
				}
			}
		}

		ratingH = ratingH / size;
		ratingH = (double) Math.round(ratingH * 100) / 100;
		user.setRating(ratingH);
		us.updateUser(user);

		int numberOfHouses = hs.numberHouses(user.getId_user());

		model.addAttribute("listSenders", listSenders);
		model.addAttribute("numberOfHouses", numberOfHouses);
		model.addAttribute("user", user);
		return "loggedUserProfile";
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

	@GetMapping("/profile/update/{email}")
	public String editUser(Model model, Principal principal) {
		String email = principal.getName();
		User user = us.getUser(email);
		model.addAttribute("user", user);
		return "userEdit";
	}

	@PostMapping("/profile/update")
	public String updateUser(@RequestParam("email") String email, Model model,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("address") String address, @RequestParam("city") String city,
			@RequestParam("postalCode") int postalCode, @RequestParam("country") String country,
			@RequestParam("additionalAddress") String additionalAddress, @RequestParam("phoneNumber") int phoneNumber,
			@RequestParam("password") String password, @RequestParam(name = "edit", defaultValue = "") int edit) {
		if (edit == 0) {
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
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
		user.setAddress(address);
		user.setCity(city);
		user.setPostalCode(postalCode);
		user.setCountry(country);
		user.setAdditionalAddress(additionalAddress);
		user.setPhoneNumber(phoneNumber);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		us.updateUser(user);

		return "redirect:/profile";
	}

	@RequestMapping(value = "/profile/delete")
	public String delete(Model model, @RequestParam(name = "email") String email) {
		System.out.print("email" + email);
		User user = us.getUser(email);
		Long id_user = user.getId_user();
		// us.deleteUser(user);
		us.deleteUser(id_user);
		return "redirect:/admin/users";
	}

}
