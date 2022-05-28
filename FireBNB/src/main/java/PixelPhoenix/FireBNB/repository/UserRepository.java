package PixelPhoenix.FireBNB.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);	
	List<User> findByFirstNameLike(String firstName);
	
	// CHANGES AM - Function to get id by email
	@Query(value = "SELECT user_id FROM `user` WHERE email = :x", nativeQuery = true)
	public Long findIdFromEmail(@Param("x") String email);
	
	//Function to get name by id
	@Query(value = "SELECT first_name, last_name FROM `user` WHERE user_id = :x", nativeQuery = true)
	public String findNameFromId(@Param("x") Long id_user);
	
	// Advanced search
	@Query(value = "SELECT * FROM `user` WHERE first_name like :x OR last_name like :x OR email like :x", nativeQuery = true)
	public Iterable<User> findWithOne(@Param("x") String one);
	
	@Query(value = "SELECT * FROM `user` WHERE first_name like :x AND last_name like :y OR "
			+ "first_name like :y AND last_name like :x OR"
			+ " email like :x AND last_name like :y OR"
			+ " email like :y AND last_name like :x OR"
			+ " email like :x AND first_name like :y OR"
			+ " email like :y AND first_name like :x", nativeQuery = true)
	public Iterable<User> findWithTwo(@Param("x") String one,@Param("y") String two);
	
	@Query(value = "SELECT * FROM `user` WHERE first_name like :x AND last_name like :y AND email like :z", nativeQuery = true)
	public Iterable<User> findWithThree(@Param("x") String first_name, @Param("y") String last_name,@Param("z") String email);
}

