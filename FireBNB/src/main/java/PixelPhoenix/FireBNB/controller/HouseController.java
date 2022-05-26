package PixelPhoenix.FireBNB.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.repository.HouseRepository;
import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.repository.ConstraintRepository;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.ServiceService;
import PixelPhoenix.FireBNB.service.ConstraintService;

@Controller
public class HouseController {
	
	@Value("${upoadDir}")
	private String uploadFolder;
	
	@Autowired 
	private HouseService hssv;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private ConstraintService constraintService;
	
	/*public House chooseService(@RequestBody House house) {
		hssv.saveHouse(house.getServices());
	}*/
	
	
	@GetMapping("/housesList")
	public String listHouses(Model model) {
		//ORIGINAL
		//Iterable<House> listHouses = hssv.getHouses();
		
		List<House> listHouses = hssv.getAllHouses();
		
		
		model.addAttribute("listHouses", listHouses);
		
		return "housesList";
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
	
	//pas très sûre de ce que c'est		
	private final Logger log = LoggerFactory.getLogger(this.getClass());

			@PostMapping("/housesList/add")
			public String add(@ModelAttribute("house") @Validated House house, Model model,  HttpServletRequest request, final @RequestParam("image") MultipartFile file) {
				
				//pour sauvegarder l'image dans le projet
				String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
				log.info("uploadDirectory:: " + uploadDirectory);
				String fileName = file.getOriginalFilename();
				String filePath = Paths.get(uploadDirectory, fileName).toString();
				log.info("FileName: " + file.getOriginalFilename());
				
				try {
					File dir = new File(uploadDirectory);
					if (!dir.exists()) {
						log.info("Folder Created");
						dir.mkdirs();
					}
					// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
				} catch (Exception e) {
					log.info("in catch");
					e.printStackTrace();
				}
				
				
				hssv.createHouse(house);
				
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
					@RequestParam(name = "photos", defaultValue = "") byte[] photos,
					@RequestParam(name = "address", defaultValue = "") String address,
					@RequestParam(name = "city", defaultValue = "") String city,
					@RequestParam(name = "postal_code", defaultValue = "") int postal_code,
					@RequestParam(name = "country", defaultValue = "") String country,
					@RequestParam(name = "additional_address", defaultValue = "") String additional_address,
					@RequestParam(name = "edit", defaultValue = "") int edit) {		
				if (edit == 0) {
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
			
	@RequestMapping(value="/housePage/{id_house}") 
	public String HousePage(@PathVariable Long id_house, Model model){
		
		Optional<House> house = hssv.getHouse(id_house);
		House house2 = house.get();
		model.addAttribute("house", house2);
		
		return "housePage";
	}
	
	//////////////////////////////

//	 @GetMapping("/housesList/add")
//	public String houseForm(Model model) {
//		model.addAttribute("house", new House());
//		
//		Iterable<Service> listServices = serviceService.getServices();
//		model.addAttribute("listServices", listServices);
//		
//		Iterable<Constraint> listConstraints = constraintService.getConstraints();
//		model.addAttribute("listConstraints", listConstraints);
//		
//		return "addHouse";
//	}
//	
	
//	@PostMapping("/housesList/add")
//	public String add(@ModelAttribute("house") @Validated House house, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return "addHouse";
//		}
//		House houseAdd = hssv.saveHouse(house);
//		model.addAttribute("house", houseAdd);
//		return "redirect:/housesList";
//	}
	
}
