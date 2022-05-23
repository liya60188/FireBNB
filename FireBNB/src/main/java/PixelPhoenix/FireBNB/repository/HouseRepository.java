package PixelPhoenix.FireBNB.repository;

import java.util.List;
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
	@Query("select house from House house where house.description like :x or house.ratingsH like: x")
	public Page<House> findByName(@Param("x") String keyword, Pageable pg);
	
	@Query(value = "SELECT COUNT(*) FROM `house` WHERE id_user = :x", nativeQuery = true)
	public int countHouseNumber(@Param("x") Long id_user);
	
	@Query(value = "SELECT * FROM `house` WHERE id_user = :x", nativeQuery = true)
	public Page<House> findListHouses(@Param("x") Long id_user, Pageable pg);
	
//	@Query("select new Service(house.id_house, service.serviceName) from House house join house.services service")
//	public Set<Service> getJoinedHouseServices();
//	
//	@Query("select new Constraint(house.id_house, constraint.constraintName) from House house join house.constraints constraint")
//	public Set<Constraint> getJoinedHouseConstraint();
}