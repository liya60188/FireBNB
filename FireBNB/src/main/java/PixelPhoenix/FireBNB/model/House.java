package PixelPhoenix.FireBNB.model;

import java.sql.Blob;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="house")
public class House{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id_house", unique = true)
		Long id_house;
		
		public Long getId_house() {
			return id_house;
		}

		public void setId_house(Long id_house) {
			this.id_house = id_house;
		}
		
		public String description;
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		} 
		
		public int ratingsH;
		public int getRatingsH() {
			return ratingsH;
		}

		public void setRatingsH(int ratingsH) {
			this.ratingsH = ratingsH;
		}
		
		public String services;
		
		public String getServices() {
			return services;
		}

		public void setServices(String services) {
			this.services = services;
		}
		
		public String constraints;

		public String getConstraints() {
			return constraints;
		}

		public void setConstraints(String constraints) {
			this.constraints = constraints;
		}

		/*

		// Services - Foreign Key
		@OneToMany(targetEntity = Service.class, cascade = CascadeType.ALL)
		@JoinColumn(name = "houseService_FK", referencedColumnName = "id_house")
		
		Set<Service> services;
		public Set<Service> getServices() {
			return services;
		}

		public void setServices(Set<Service> services) {
			this.services = services;
		}
		
		//Constraints - Foreign Key
		@OneToMany(targetEntity = Constraint.class, cascade = CascadeType.ALL)
		@JoinColumn(name = "houseConstraint_FK", referencedColumnName = "id_house")
		Set<Constraint> constraints;
		public Set<Constraint> getConstraints() {
			return constraints;
		}

		public void setConstraints(Set<Constraint> constraints) {
			this.constraints = constraints;
		}

*/
		/*
		//Photos - Foreign Key
		@OneToMany(targetEntity = Photo.class, cascade = CascadeType.ALL)
		@JoinColumn(name = "housePhoto_FK", referencedColumnName = "id_house")
		Set<Photo> photos;		
		public Set<Photo> getPhotos() {
			return photos;
		}

		public void setPhotos(Set<Photo> photos) {
			this.photos = photos;
		}
		*/
		
//		@Lob
//		@Column(columnDefinition = "LONGBLOB")
//		public String photos;
//
//		public String getPhotos() {
//			return photos;
//		}
//
//		public void setPhotos(String photos) {
//			this.photos = photos;
//		}
		
		@Lob
		@Column(name="photos", length=Integer.MAX_VALUE, nullable=true)
		public byte[] photos;
		
		public byte[] getPhotos() {
			return photos;
		}

		public void setPhotos(byte[] photos) {
			this.photos = photos;
		}
		
//		public Blob photos;
//
//		public Blob getPhotos() {
//			return photos;
//		}
//
//		public void setPhotos(Blob photos) {
//			this.photos = photos;
//		}

		public Long id_user;
		
		public Long getId_user() {
			return id_user;
		}

		public void setId_user(Long id_user) {
			this.id_user = id_user;
		}

		public String address;
		
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String city;
		
		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}
		
		public int postal_code;
		
		public int getPostal_code() {
			return postal_code;
		}

		public void setPostal_code(int postal_code) {
			this.postal_code = postal_code;
		}
		
		public String country;

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
		
		public String additional_address;

		public String getAdditional_address() {
			return additional_address;
		}

		public void setAdditional_address(String additional_address) {
			this.additional_address = additional_address;
		}

		public House(Long id_house,String description, int ratingsH, String services, String constraints, byte[] photos, Long id_user, String address, String city, int postal_code, String country, String additional_address) {
			this.id_house = id_house;
			this.description = description;
			this.ratingsH = ratingsH;
			this.services = services;
			this.constraints = constraints;
			this.photos = photos;	
			this.id_user = id_user;
			this.address = address;
			this.city = city;
			this.postal_code = postal_code;
			this.country = country;
			this.additional_address = additional_address;
		}
		
		public House() {}
}
