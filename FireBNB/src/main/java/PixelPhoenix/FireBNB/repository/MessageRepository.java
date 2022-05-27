package PixelPhoenix.FireBNB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PixelPhoenix.FireBNB.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	@Query("select message from Message message where message.subject like :x")
	public Page<Message> findByName(@Param("x") String mc, Pageable pg);
	
	@Query(value="SELECT * FROM `messaging` WHERE id_receiver = :x", nativeQuery = true)
	public Iterable<Message> findListMessages(@Param("x") Long id_user);
	
}