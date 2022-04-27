package PixelPhoenix.FireBNB.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
