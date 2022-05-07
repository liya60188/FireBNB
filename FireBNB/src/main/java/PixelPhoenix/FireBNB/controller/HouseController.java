package PixelPhoenix.FireBNB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;

import PixelPhoenix.FireBNB.model.House;
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
	/*
	
	@RequestMapping(value="/housePage/{id}")
	public String house(Model model,@PathVariable long id) {
		 Optional<House> house = hssv.getHouse(hssv.getHouse(id));
		 model.addAttribute("house",house);
		 
		 return "house";
	}
	
	*/
	
	

}
