package PixelPhoenix.FireBNB.model;

import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
@Table(name="ratings")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rating", unique = true)
	Long id_rating;
	Long id_userSender;
	Long id_userReceiver;
	Long id_house;
	String description;
	int value;
	String type;
	
	public Long getId_rating() {
		return id_rating;
	}

	public void setId_rating(Long id_rating) {
		this.id_rating = id_rating;
	}

	// Rating of a user
	public Rating(Long id_rating, int value, String type, Long id_userSender, Long id_userReceiver, String description) {
		this.id_rating = id_rating;
		this.id_userSender = id_userSender;
		this.id_userReceiver = id_userReceiver;
		this.description = description;
		this.value = value;
		this.type = type;
	}
	
	// Rating of a House
	public Rating(Long id_rating, Long id_userSender, Long id_house, String description, int value, String type) {
		this.id_rating = id_rating;
		this.id_userSender = id_userSender;
		this.id_house = id_house;
		this.description = description;
		this.value = value;
		this.type = type;
	}

	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Long getId_userSender() {
		return id_userSender;
	}



	public void setId_userSender(Long id_userSender) {
		this.id_userSender = id_userSender;
	}



	public Long getId_userReceiver() {
		return id_userReceiver;
	}



	public void setId_userReceiver(Long id_userReceiver) {
		this.id_userReceiver = id_userReceiver;
	}



	public Long getId_house() {
		return id_house;
	}



	public void setId_house(Long id_house) {
		this.id_house = id_house;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Rating() {}
}