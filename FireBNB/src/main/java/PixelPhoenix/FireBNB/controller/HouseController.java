package PixelPhoenix.FireBNB.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.repository.HouseRepository;
import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.ServiceService;

@Controller
public class HouseController {
	
	@Autowired 
	private HouseService hssv;
	@Autowired
	private ServiceService serviceService;
	
	/*public House chooseService(@RequestBody House house) {
		hssv.saveHouse(house.getServices());
	}*/
	
	@GetMapping("/housesList")
	public String listHouses(Model model) {
		Iterable<House> listHouses = hssv.getHouses();
		model.addAttribute("listHouses", listHouses);
		
		return "housesList";
	}
	
	@GetMapping("/housesList/add")
	public String houseForm(Model model) {
		Iterable<Service> listServices = serviceService.getServices();
		model.addAttribute("listServices", listServices);
		
		model.addAttribute("house", new House());
		return "addHouse";
	}
	
	// Tests for Services
	/*@Autowired
	private ServiceRepository serviceRepository;
	@GetMapping("/housesList/add")
    public ModelAndView houseForm() {
        House house = new House();
        ModelAndView mav = new ModelAndView("addHouse");
        mav.addObject("house", house);
         
        List<Service> listServices = (List<Service>) serviceRepository.findAll();
         
        mav.addObject("services", listServices);
         
        return mav;    
    }   */

	@PostMapping("/housesList/add")
	public String add(@ModelAttribute("house") @Validated House house, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addHouse";
		}
		House houseAdd = hssv.saveHouse(house);
		model.addAttribute("house", houseAdd);
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
			@RequestParam(name = "edit", defaultValue = "") int edit) {
		if (edit == 0) {
			model.addAttribute("id_house", id_house);
			model.addAttribute("description", description);
			model.addAttribute("services", services);
			model.addAttribute("constraints", constraints);
			model.addAttribute("ratingsH", ratingsH);
			model.addAttribute("photos", photos);
			return "housesEdit";
		} else {
			Optional<House> ot = hssv.getHouse(id_house);
			House house = ot.get();
			house.setDescription(description);
			//house.setServices(services);
			house.setConstraints(constraints);
			house.setRatingsH(ratingsH);
			house.setPhotos(photos);
			hssv.saveHouse(house);
			return "redirect:/housesList";
		}
	}

	
	/*
	
	@RequestMapping(value="/housePage/{id}")
	public String house(Model model,@PathVariable long id) {
		 Optional<House> house = hssv.getHouse(hssv.getHouse(id));
		 model.addAttribute("house",house);
		 
		 return "house";
	}
	
	*/
	
	

}
