package PixelPhoenix.FireBNB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import PixelPhoenix.FireBNB.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
	@Query("select photo from Photo photo where photo.id_photo like :x")
	public Page<Photo> findByName(@Param("x") Long id_photo, Pageable pg);

}
