package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.repository.HouseRepository;

@Service
public class HouseService {
	
	 @Autowired
	    private HouseRepository hsrp;

	 public Optional<House> getHouse(final Long id){
			return hsrp.findById(id);
		}
		
	 public Iterable<House> getHouses(){
			return hsrp.findAll();
		}
	 
	 public void deleteHouse(final Long id) {
		 hsrp.deleteById(id);
	    }
	 
	 @PutMapping(value = "listHouses/housesEdit")
	 public House saveHouse(@RequestBody House house) {
		 House savedHouse = hsrp.save(house);
         return savedHouse;
	    }
}
