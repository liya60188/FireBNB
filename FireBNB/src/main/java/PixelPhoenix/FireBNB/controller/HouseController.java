package PixelPhoenix.FireBNB.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.repository.HouseRepository;
import PixelPhoenix.FireBNB.service.HouseService;

@Controller
public class HouseController {
	
	@Autowired 
	private HouseService hssv;
	
	@GetMapping("/housesList")
	public String listHouses(Model model) {
		Iterable<House> listHouses = hssv.getHouses();
		model.addAttribute("listHouses", listHouses);
		
		return "housesList";
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
			house.setServices(services);
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
