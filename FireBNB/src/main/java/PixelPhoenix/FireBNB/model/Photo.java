package PixelPhoenix.FireBNB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="photos")
public class Photo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_photo", unique = true)
	Long id_photo;

	public Long getId_photo() {
		return id_photo;
	}

	public void setId_photo(Long id_photo) {
		this.id_photo = id_photo;
	}
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	String housePhoto;
	
	public String getHousePhoto() {
		return housePhoto;
	}

	public void setHousePhoto(String housePhoto) {
		this.housePhoto = housePhoto;
	}
	
	
	public Photo(Long id_photo, String housePhoto) {
		this.id_photo = id_photo;
		this.housePhoto = housePhoto;
	}
	
	public Photo() {}

}
