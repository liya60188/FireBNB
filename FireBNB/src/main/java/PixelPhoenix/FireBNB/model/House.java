package PixelPhoenix.FireBNB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="house")
public class House{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id_house", unique = true)
		int id_house;
		public int getId_home() {
			return id_house;
		}

		public void setId_home(int id_house) {
			this.id_house = id_house;
		}
		
		String description;
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		} 
		
		int ratingsH;
		public int getRatingsH() {
			return ratingsH;
		}

		public void setRatingsH(int ratingsH) {
			this.ratingsH = ratingsH;
		}
		
		String services;	
		public String getServices() {
			return services;
		}

		public void setServices(String services) {
			this.services = services;
		}

		String constraints;
		public String getConstraints() {
			return constraints;
		}

		public void setConstraints(String constraints) {
			this.constraints = constraints;
		}
		
		String photos;
		public String getPhotos() {
			return photos;
		}

		public void setPhotos(String photos) {
			this.photos = photos;
		} 
		
		public House(int id_house,String description, int ratingsH, String services, String constraints,String photos) {
			this.id_house = id_house;
			this.description = description;
			this.ratingsH = ratingsH;
			this.services = services;
			this.constraints = constraints;
			this.photos = photos;	
		}
		
		public House() {}


}
