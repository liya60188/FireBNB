package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.MessageService;
import PixelPhoenix.FireBNB.service.RatingService;
import PixelPhoenix.FireBNB.service.UserService;

@Controller
public class RatingController {
	@Autowired 
	private RatingService ratingService;
	@Autowired
	private UserService userService;
	@Autowired
	private HouseService houseService;
	
	// Should redirect to house profile
	@GetMapping(value = {"/rating/add/HouseRating/{id_house}"})
	public String ratingHouseForm(Model model,
			@PathVariable("id_house") Long id_house) {
		 Rating rating = new Rating();
		 model.addAttribute("rating", rating);
		 rating.setId_house(id_house);
		 return "addRatingHouse";
	}
	
	@PostMapping(value = {"/rating/add/HouseRating"})
	public String addHouseRating(@ModelAttribute("rating") @Validated Rating rating, Model model, 
			Principal principal
			) {
		
		Long id_house = rating.getId_house();
		String emailUser = principal.getName();
		User currentUser = userService.getUser(emailUser);
		ratingService.createHouseRating(rating, currentUser.getId_user());
		return "redirect:/houseProfile?id_house="+id_house;
	}
	
	// Should redirect to user profile
	@GetMapping(value = {"/rating/add/UserRating/{id_userReceiver}"})
	public String ratingUserForm(Model model,
			@PathVariable("id_userReceiver") Long id_userReceiver) {
		 Rating rating = new Rating();
		 model.addAttribute("rating", rating);
		 rating.setId_userReceiver(id_userReceiver);
		 return "addRatingUser";
	}
	
	@PostMapping(value = {"/rating/add/UserRating"})
	public String addUserRating(@ModelAttribute("rating") @Validated Rating rating, Model model, 
			Principal principal
			) {
		
		String emailUser = principal.getName();
		User currentUser = userService.getUser(emailUser);
		ratingService.createUserRating(rating, currentUser.getId_user());
		
		Optional<User> u = userService.getUserId(rating.getId_userReceiver());
		User userReceiver = u.get();
		return "redirect:/profile$email=" + userReceiver.getEmail();
	}
}