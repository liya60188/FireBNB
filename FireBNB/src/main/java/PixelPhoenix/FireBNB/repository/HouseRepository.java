package PixelPhoenix.FireBNB.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Service;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>{
	
	@Query("select house from House house where house.description like :x or house.address like :x or house.ratingsH like :x or house.city like :x or house.constraints like :x or house.services like :x or house.postal_code like :x or house.country like :x")
	public Page<House> findByName(@Param("x") String keyword, Pageable pg);
	
	//List<House> findByDescriptionAndAddress(String description, String address);
	
}

