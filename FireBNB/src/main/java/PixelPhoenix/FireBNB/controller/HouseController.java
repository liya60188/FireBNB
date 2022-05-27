package PixelPhoenix.FireBNB.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.repository.HouseRepository;
import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.repository.ConstraintRepository;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.ServiceService;
import PixelPhoenix.FireBNB.service.UserService;
import PixelPhoenix.FireBNB.service.ConstraintService;
import PixelPhoenix.FireBNB.service.RatingService;

@Controller
public class HouseController {
	
	@Autowired 
	private HouseService hssv;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private ConstraintService constraintService;
	@Autowired
	private UserService us;
	@Autowired
	private RatingService ratingService;
	
	/*public House chooseService(@RequestBody House house) {
		hssv.saveHouse(house.getServices());
	}*/
	
	
	@GetMapping("/housesList")
	public String listHouses(Model model) {
		Iterable<House> listHouses = hssv.getHouses();
		model.addAttribute("listHouses", listHouses);
		
		return "housesList";
	}
	
//	@GetMapping("/profile/houses/{email}")
//	public String listUserHouses(@PathVariable("email") String email, Model model) {
//		User user = us.getUser(email);
//		Iterable<House> listUserHouses = hssv.getUserHouses(user.getId_user());
//		model.addAttribute("listUserHouses", listUserHouses);
//		model.addAttribute("user", user);
//		
//		return "housesUserList";
//	}
	@GetMapping("/profile/houses")
	public String listUserHouses(Principal principal, Model model) {
		String emailLoggedUser = principal.getName();
		User loggedUser = us.getUser(emailLoggedUser);
		Iterable<House> listUserHouses = hssv.getUserHouses(loggedUser.getId_user());
		
		model.addAttribute("listUserHouses", listUserHouses);
		model.addAttribute("loggedUser", loggedUser);
		
		return "housesUserList";
	}
	
	//List of houses Page with search bar
	@Autowired
	private HouseRepository houseRepository;
	@RequestMapping(value = {"/housesList", "/housesList/search"})
	public String search(Model model, @RequestParam(name = "motCle", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Page<House> listHouses = houseRepository.findByName("%" + keyword + "%", PageRequest.of(page, size));
		int[] pages = new int[listHouses.getTotalPages()];
		model.addAttribute("listHouses", listHouses.getContent());
		model.addAttribute("motC", keyword);
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", page);
		return "housesList";
	}
	
	
			
	// Tests for Services
	@Autowired
	private ServiceRepository serviceRepository;
	@GetMapping("/housesList/add")
	public String houseForm(Model model) {
		 House house = new House();
		 //ModelAndView mav = new ModelAndView("addHouse");
		 model.addAttribute("house", house);
		         
		 Iterable<Service> listServices = serviceService.getServices();
		 model.addAttribute("listServices", listServices);
		        
		 Iterable<Constraint> listConstraints = constraintService.getConstraints();
		 model.addAttribute("listConstraints", listConstraints);
		         
		 return "addHouse";    
		 }
	

	@PostMapping("/housesList/add")
	public String add(@ModelAttribute("house") @Validated House house, Model model) {
		hssv.createHouse(house);
		//model.addAttribute("house", houseAdd);
		return "redirect:/housesList";
	}
			
	
	@RequestMapping(value = "/housesList/delete")
	public String delete(Model model, @RequestParam(name = "id_house", defaultValue = "") Long id_house) {
		hssv.deleteHouse(id_house);
		return "redirect:/housesList";
	}
			
	@RequestMapping(value = "/listHouses/edit")
	public String edit(Model model, @RequestParam(name = "id_house", defaultValue = "") Long id_house,
		@RequestParam(name = "description", defaultValue = "") String description,
		@RequestParam(name = "services", defaultValue = "") String services,
		@RequestParam(name = "constraints", defaultValue = "") String constraints,
		@RequestParam(name = "ratingsH", defaultValue = "") double ratingsH,
		@RequestParam(name = "photos", defaultValue = "") String photos,
		@RequestParam(name = "address", defaultValue = "") String address,
		@RequestParam(name = "city", defaultValue = "") String city,
		@RequestParam(name = "postal_code", defaultValue = "") int postal_code,
		@RequestParam(name = "country", defaultValue = "") String country,
		@RequestParam(name = "additional_address", defaultValue = "") String additional_address,
		@RequestParam(name = "id_user", defaultValue = "") Long id_user,
		@RequestParam(name = "edit", defaultValue = "") int edit) {	
		if (edit == 0) {
			Optional<User> u = us.getUserId(id_user); 
			User user = u.get();
			model.addAttribute("user",user);
			model.addAttribute("id_house", id_house);
			model.addAttribute("description", description);
			model.addAttribute("services", services);
			model.addAttribute("constraints", constraints);
			model.addAttribute("ratingsH", ratingsH);
			model.addAttribute("photos", photos);
			model.addAttribute("address", address);
			model.addAttribute("city", city);
			model.addAttribute("postal_code", postal_code);
			model.addAttribute("country", country);
			model.addAttribute("additional_address", additional_address);
			return "housesEdit";
		} else {
			Optional<House> ot = hssv.getHouse(id_house);
			House house = ot.get();
			house.setDescription(description);
			//house.setServices(services);
			//house.setConstraints(constraints);
			house.setRatingsH(ratingsH);
			house.setPhotos(photos);
			house.setAddress(address);
			house.setCity(city);
			house.setPostal_code(postal_code);
			house.setCountry(country);
			house.setAdditional_address(additional_address);
			hssv.saveHouse(house);
			return "redirect:/housesList";
		}
	}
	
			
	@RequestMapping(value="/houseProfile/{id_house}") 
	public String HousePage(@PathVariable Long id_house, Model model, Principal principal){
		//House housePage = house.get();
		
		// CHANGED BY AMANDA - Show all Ratings + Average
		Iterable<Rating> listHouseRatings = ratingService.getRatingsByHouse(id_house);
		model.addAttribute("listHouseRatings", listHouseRatings);
		
		Optional<House> house = hssv.getHouse(id_house);
		House house2 = house.get();
		double ratingH = 0.0;
		
		int size = 0;
		for (Rating rating : listHouseRatings) {
			 ratingH += rating.getValue();
			size += 1;
		}
		
		ratingH = ratingH / size;
		ratingH = (double) Math.round(ratingH * 100) / 100;
		house2.setRatingsH(ratingH);
		hssv.saveHouse(house2);
		
		String emailPrincipal = principal.getName();
		Long houseOwnerId = house2.getId_user();
		String houseOwner = us.getNameFromId(houseOwnerId);
		Long currentUserId = us.getIdByEmail(emailPrincipal);
		
		model.addAttribute("houseOwner", houseOwner);
		model.addAttribute("houseOwnerId", houseOwnerId);
		model.addAttribute("currentUserId", currentUserId);
		
		model.addAttribute("house", house2);
		
		return "houseProfile";
	}
	
}