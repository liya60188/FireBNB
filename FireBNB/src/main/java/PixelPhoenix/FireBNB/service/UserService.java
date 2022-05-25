package PixelPhoenix.FireBNB.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PixelPhoenix.FireBNB.model.Role;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository ur;
	
	public User getUser(String email){
		return ur.findByEmail(email);	
	}
	public Optional<User> getUserId(long id){
		return ur.findById(id);	
	}
	public Iterable<User> getUsers(){
		return ur.findAll();
	}
	
	public List<User> findByName(String name){
		return ur.findByFirstNameLike("%"+name+"%");
	}
	
	public void createUser(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("USER");
		ur.save(user);
	}
	public void createAdmin(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("ADMIN");
		ur.save(user);
	}
	
	public boolean isUserExist(String email) {
		User u = ur.findByEmail(email);
		if(u != null) {
			return true;
		}
		return false;
	}

	public User updateUser(User user ) {
		return ur.save(user);	
	}
	
	public void deleteUser(final Long id_user) {
		ur.deleteById(id_user);
		//ur.delete(user);
	}
}
