package PixelPhoenix.FireBNB.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
//		Role userRole = new Role("user");
//		Set<Role> roles = new HashSet<>();
//		roles.add(userRole);
//		user.setRoles(roles);
		user.setRole("user");
		ur.save(user);
	}
	public void createAdmin(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
//		Role userRole = new Role("admin");
//		Set<Role> roles = new HashSet<>();
//		roles.add(userRole);
//		user.setRoles(roles);
		user.setRole("admin");
		ur.save(user);
	}
	
	public boolean isUserExist(String email) {
		User u = ur.findByEmail(email);
		if(u != null) {
			return true;
		}
		return false;
	}
}
