package PixelPhoenix.FireBNB.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.repository.HouseRepository;
import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.repository.ConstraintRepository;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.MessageService;
import PixelPhoenix.FireBNB.service.ServiceService;
import PixelPhoenix.FireBNB.service.UserService;
import PixelPhoenix.FireBNB.service.ConstraintService;

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
	private MessageService ms;
	
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
		@RequestParam(name = "ratingsH", defaultValue = "") int ratingsH,
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
	public String HousePage(@PathVariable Long id_house, Model model){
		//House housePage = house.get();
		
		Optional<House> house = hssv.getHouse(id_house);
		House house2 = house.get();
		model.addAttribute("house", house2);
		
		return "houseProfile";
	}
	
	@RequestMapping(value="/bookHouse")
	public String bookHousePage(@RequestParam("id_house") Long id_house, Principal principal, Model model) {
		Optional<House> house = hssv.getHouse(id_house);
		House house2 = house.get();
		model.addAttribute("id_houseReceive", id_house);
		model.addAttribute("house",house2);
		
		String emailLoggedUser = principal.getName();
		User loggedUser = us.getUser(emailLoggedUser);
		Iterable<House> listUserHouses = hssv.getUserHouses(loggedUser.getId_user());
		
		model.addAttribute("listUserHouses", listUserHouses);
		model.addAttribute("loggedUser", loggedUser);
		
		return "bookHouse";
	}
	
//	@PostMapping(value="/bookHouse")
//	public String bookHouse(Model model, @ModelAttribute House house, BindingResult errors, @RequestParam("id_houseReceive") Long id_houseReceive) throws ParseException {
//		
//		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
//		
//		Date begin_date = s.parse(house.begin_date);
//		Date end_date = s.parse(house.end_date);
//		String isBookedError = null;
//		
//
//		Optional<House> ot = hssv.getHouse(id_houseReceive);
//		House house2= ot.get();
//		Date begin_date_exist = s.parse(house2.getBegin_date());
//		Date end_date_exist = s.parse(house2.getEnd_date());
//		
//		if(begin_date_exist == null && end_date_exist == null) {
//			house2.setBegin_date(house2.getBegin_date());
//			house2.setEnd_date(house2.getEnd_date());
//			isBookedError = "";
//		}else if(begin_date.before(begin_date_exist) && end_date.after(begin_date_exist) ||
//		begin_date.before(end_date_exist) && end_date.after(end_date_exist) ||
//		begin_date.before(begin_date_exist) && end_date.after(end_date_exist) ||
//		begin_date.before(begin_date_exist) && end_date.after(end_date_exist)) {
//			isBookedError = "Date overlap";
//		}else {
//			house2.setBegin_date(house2.getBegin_date());
//			house2.setEnd_date(house2.getEnd_date());
//			isBookedError = "";
//		}
//		
//		
//		model.addAttribute("isBookedError", isBookedError);
//		model.addAttribute("id_house",id_houseReceive);
//		
//		return"bookHouse";
//	}
	
	
	@PostMapping(value="/bookHouse")
	public String bookHouse(Model model, Principal principal, @RequestParam("id_houseReceive") Long id_houseReceive,
			@RequestParam("begin_date") String begin_dateString,
			@RequestParam("end_date") String end_dateString,
			@RequestParam("id_houseSend") Long id_houseSend) throws ParseException {
		
		String emailLoggedUser = principal.getName();
		User loggedUser = us.getUser(emailLoggedUser);
		
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		
		Date begin_date = s.parse(begin_dateString);
		Date end_date = s.parse(end_dateString);
		String isBookedError = null;
		

		Optional<House> ot = hssv.getHouse(id_houseReceive);
		House house = ot.get();
		Long id_Receiver = house.getId_user();
		Date begin_date_exist = house.getBegin_date();
		Date end_date_exist = house.getEnd_date();
		
		if(begin_date_exist == null && end_date_exist == null) {
			house.setBegin_date(begin_date);
			house.setEnd_date(end_date);
			isBookedError = "";
			
			Message message = new Message();
			message.setId_sender(loggedUser.getId_user());
			message.setId_receiver(id_Receiver);
			message.setSubject(house.getAddress() + ", " + house.getCity() + ", " + house.getCountry());
			message.setContent(loggedUser.getFirstName()+" "+loggedUser.getLastName()+" has made an exchange proposition for your house.\n "
					+ "Begin date: " + begin_date +"\n" + "End date: " + end_date);
			message.setId_house_receiver(id_houseReceive);
			message.setId_house_sender(id_houseSend);
			ms.saveMessage(message);
			
			return "redirect:/housesList";
			
		}else if(begin_date.before(begin_date_exist) && end_date.after(begin_date_exist) ||
		begin_date.before(end_date_exist) && end_date.after(end_date_exist) ||
		begin_date.before(begin_date_exist) && end_date.after(end_date_exist) ||
		begin_date.after(begin_date_exist) && end_date.before(end_date_exist)) {
			isBookedError = "Date overlap";
			model.addAttribute("isBookedError", isBookedError);
			model.addAttribute("id_houseReceive",id_houseReceive);
			model.addAttribute("house", house);
			
			Iterable<House> listUserHouses = hssv.getUserHouses(loggedUser.getId_user());
			
			model.addAttribute("listUserHouses", listUserHouses);
			model.addAttribute("loggedUser", loggedUser);
			
			return"bookHouse";
			
		}else {
			house.setBegin_date(begin_date);
			house.setEnd_date(end_date);
			isBookedError = "";
			
			Message message = new Message();
			message.setId_sender(loggedUser.getId_user());
			message.setId_receiver(id_Receiver);
			message.setSubject(house.getAddress() + ", " + house.getCity() + ", " + house.getCountry());
			message.setContent(loggedUser.getFirstName()+" "+loggedUser.getLastName()+" has made an exchange proposition for your house.\n "
					+ "Begin date: " + begin_date +"\n" + "End date: " + end_date);
			message.setId_house_receiver(id_houseReceive);
			message.setId_house_sender(id_houseSend);
			ms.saveMessage(message);
			
			return "redirect:/housesList";
		}
			
	}
	
}