package PixelPhoenix.FireBNB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>{
	@Query("select house from House house where house.id_house like :x")
	public Page<House> findByName(@Param("x") Long id_house, Pageable pg);
}

