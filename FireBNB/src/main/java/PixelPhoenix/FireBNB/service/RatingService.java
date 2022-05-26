package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.repository.MessageRepository;
import PixelPhoenix.FireBNB.repository.RatingRepository;

@Service
public class RatingService {
	@Autowired
	private RatingRepository ratingRepository;
	
	public Optional<Rating> getRating(final Long id){
		return ratingRepository.findById(id);
	}
	
	public Iterable<Rating> getRatings(){
		return ratingRepository.findAll();
	}
	
	public Iterable<Rating> getRatingsByHouse(Long id_house){
		return ratingRepository.findById_house(id_house);
	}
	
	public Iterable<Rating> getRatingsByReceiver(Long id_userReceiver){
		return ratingRepository.findById_userReceiver(id_userReceiver);
	}
 
	public void deleteRating(final Long id) {
		ratingRepository.deleteById(id);
    }
 
	 @PutMapping(value = "ratingsList")
	 public Rating saveRating(@RequestBody Rating rating) {
		 Rating savedRating = ratingRepository.save(rating);
	     return savedRating;
	 }
	 
	 public void createHouseRating(Rating rating, Long id_userSender) {
			rating.setType("HOUSE");
			rating.setId_userSender(id_userSender);
			ratingRepository.save(rating);
	}
	 
	 public void createUserRating(Rating rating, Long id_userSender) {
			rating.setType("USER");
			rating.setId_userSender(id_userSender);
			ratingRepository.save(rating);
	}
	 
	/*public Page<Rating> getRatingBySearch(String mc, int page, int size) {
		return ratingRepository.findByName("%" + mc + "%", PageRequest.of(page, size));
	}*/
}