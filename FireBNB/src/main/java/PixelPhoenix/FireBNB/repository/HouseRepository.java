package PixelPhoenix.FireBNB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>{

}

