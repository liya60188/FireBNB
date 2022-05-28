package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

	@GetMapping("/profile/{email}")
	public String userProfile(Model model, @PathVariable("email") String email, Principal principal) {
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
				if (sender.getId_user() == rating.getId_userSender()) {
					listSenders.add(sender.getFirstName() + " " + sender.getLastName());
				}
			}
		}

		ratingH = ratingH / size;
		ratingH = (double) Math.round(ratingH * 100) / 100;
		user.setRating(ratingH);
		user.setRatingSize(size);
		us.updateUser(user);

		int numberOfHouses = hs.numberHouses(user.getId_user());

		// Best 3 or less houses
		/*
		 * Iterable<House> listUserHouses = hs.getUserHouses(user.getId_user());
		 * Map<House, Integer> bestHouses = new HashMap<House, Integer>(); List<Integer>
		 * houseRatingSizeList = new ArrayList<>();
		 * 
		 * int greatCounter = 0; for (House house : listUserHouses) {
		 * houseRatingSizeList.add(house.getRatingSize()); if (house.getRatingsH() >
		 * 4.5) { greatCounter += 1; } }
		 * 
		 * // Random 3 best houses from 4 star filter /* List<House> keys = new
		 * ArrayList<>(bestHouses.keySet()); List<House> random = new ArrayList<>();
		 * Random rand = new Random();
		 */

		/*
		 * Iterable<House> bestFromDB = hsrp.findBestHouses(user.getId_user()); for
		 * (House best : bestFromDB) { bestHouses.put(best, best.getRatingSize()); }
		 * 
		 * if(bestHouses.size() > 3) { while(bestHouses.size() > 3) { for (House house :
		 * bestFromDB) { if(bestHouses.containsKey(house)) { bestHouses.remove(house); }
		 * if(bestHouses.size() == 3) { break; } } } }
		 * 
		 * if(bestHouses.size() < 3) { while(bestHouses.size() < 3) { for (House house :
		 * listUserHouses) { if(!bestHouses.containsKey(house)) { bestHouses.put(house,
		 * house.getRatingSize()); } if(bestHouses.size() == 3) { break; } } } }
		 * 
		 * //for (House house : listUserHouses) {
		 * 
		 * 
		 * /*if (greatCounter > 3) { // S'il y a plus de 3 maisons à 4.5, prendre 3
		 * 1ères if (house.ratingsH > 4.5) { bestHouses.put(house,
		 * house.getRatingSize()); } } if (greatCounter <= 3) { for (House best :
		 * bestFromDB) { bestHouses.put(best, best.getRatingSize()); } if
		 * (bestHouses.size() == 3) { break; } bestHouses.put(house,
		 * house.getRatingSize()); }
		 */

		// }

		// Collections.sort(houseRatingSizeList);

		/*
		 * 
		 * if(bestHouses.size() > 3) { for (int i = 0; i < 3; i++) { House key =
		 * keys.get(rand.nextInt(keys.size())); random.add(key); } } else { for(House
		 * house : bestHouses.keySet()) { random.add(house); } }
		 */

		/*
		 * do { for(House house : bestHouses.keySet()) { if(house.getRatingSize() ==
		 * houseRatingSizeList.get(houseRatingSizeList.size() - 1)) {
		 * //bestHouses.remove(house);
		 * houseRatingSizeList.remove(houseRatingSizeList.size() - 1); } } }
		 * while(bestHouses.size() > 3);
		 */

		// model.addAttribute("houseRatingSizeList", houseRatingSizeList);
		// model.addAttribute("bestHouses", bestHouses);
		// model.addAttribute("random", random);
		model.addAttribute("listSenders", listSenders);
		model.addAttribute("user", user);
		model.addAttribute("numberOfHouses", numberOfHouses);
		return "userProfile";
	}

	@GetMapping("/profile")
	public String userProfile(Model model, Principal principal) {

		String emailLoggedUser = principal.getName();
		User user = us.getUser(emailLoggedUser);

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
				if (sender.getId_user() == rating.getId_userSender()) {
					listSenders.add(sender.getFirstName() + " " + sender.getLastName());
				}
			}
		}

		ratingH = ratingH / size;
		ratingH = (double) Math.round(ratingH * 100) / 100;
		user.setRating(ratingH);
		user.setRatingSize(size);
		us.updateUser(user);

		int numberOfHouses = hs.numberHouses(user.getId_user());

		model.addAttribute("size", size);
		model.addAttribute("listSenders", listSenders);
		model.addAttribute("numberOfHouses", numberOfHouses);
		model.addAttribute("user", user);
		return "loggedUserProfile";
	}
	
	@GetMapping("/adminProfile")
	public String adminProfile(Model model, Principal principal) {

		String emailLoggedUser = principal.getName();
		User user = us.getUser(emailLoggedUser);
		
		model.addAttribute("user",user);
		
		return "adminProfile";
		
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
			@RequestParam(name = "edit", defaultValue = "") int edit) {
		if (edit == 0) {
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("address", address);
			model.addAttribute("city", city);
			model.addAttribute("postalCode", postalCode);
			model.addAttribute("country", country);
			model.addAttribute("additionalAddress", additionalAddress);
			model.addAttribute("phoneNumber", phoneNumber);
			model.addAttribute("descprition",description);
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
