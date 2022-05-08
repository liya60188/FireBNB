package PixelPhoenix.FireBNB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.Constraint;

@Repository
public interface ConstraintRepository extends JpaRepository<Constraint, Long>{
	@Query("select service from Constraint constraint where constraint.id_constraint like :x")
	public Page<Constraint> findByName(@Param("x") Long id_constraint, Pageable pg);
}
