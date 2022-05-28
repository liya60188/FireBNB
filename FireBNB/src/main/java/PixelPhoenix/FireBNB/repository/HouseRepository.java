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
	public Iterable<House> findListHouses(@Param("x") Long id_user);
	
	@Query(value = "SELECT * FROM `house` WHERE ratingsH > 4.0 AND id_user = :x", nativeQuery = true)
	public Iterable<House> findBestHouses(@Param("x") Long id_user);
	
	// Advanced search try
	@Query(value = "SELECT * FROM `house` WHERE country like :x OR city like :x OR address like :x", nativeQuery = true)
	public Iterable<House> findWithOne(@Param("x") String one);
	
	@Query(value = "SELECT * FROM `house` WHERE country like :x AND city like :y OR "
			+ "country like :y AND city like :x OR"
			+ " address like :x AND city like :y OR"
			+ " address like :y AND city like :x OR"
			+ " address like :x AND country like :y OR"
			+ " address like :y AND country like :x", nativeQuery = true)
	public Iterable<House> findWithTwo(@Param("x") String one, @Param("y") String two);

	@Query(value = "SELECT * FROM `house` WHERE country like :x AND city like :y AND address like :z", nativeQuery = true)
	public Iterable<House> findWithThree(@Param("x") String country, @Param("y") String city, @Param("z") String address);

//	@Query("select new Service(house.id_house, service.serviceName) from House house join house.services service")
//	public Set<Service> getJoinedHouseServices();
//	
//	@Query("select new Constraint(house.id_house, constraint.constraintName) from House house join house.constraints constraint")
//	public Set<Constraint> getJoinedHouseConstraint();
}