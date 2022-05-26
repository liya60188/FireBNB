package PixelPhoenix.FireBNB.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	//@Query("select ratings from Ratings ratings where rating.id_userSender like :x")
	//public Optional<Rating> findAllById_userSender(@Param("x") Long id_userSender);
}