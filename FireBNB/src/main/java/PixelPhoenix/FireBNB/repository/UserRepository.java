package PixelPhoenix.FireBNB.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findUserByUsername(String username);
}

