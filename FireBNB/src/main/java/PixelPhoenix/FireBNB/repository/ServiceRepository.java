package PixelPhoenix.FireBNB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
	@Query("select service from Service service where service.id_service like :x")
	public Page<Service> findByName(@Param("x") Long id_service, Pageable pg);
}
