package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository ur;
	
	public Optional<User> getUser(final Long id){
		return ur.findById(id);
	}
	public Iterable<User> getUsers(){
		return ur.findAll();
	}
	
}
