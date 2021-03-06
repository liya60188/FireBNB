package PixelPhoenix.FireBNB.model;

import java.util.Date;
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
import javax.persistence.PrePersist;
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
		
		public double ratingsH;
		
		public double getRatingsH() {
			return ratingsH;
		}

		public void setRatingsH(double ratingsH) {
			this.ratingsH = ratingsH;
		}
		
		public int ratingSize;
		
		public int getRatingSize() {
			return ratingSize;
		}

		public void setRatingSize(int ratingSize) {
			this.ratingSize = ratingSize;
		}
		
		@PrePersist
		private void onCreate() {
			this.ratingsH = 0.0;
			this.ratingSize = 0;
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
		public String photo1;
		public String getPhoto1() {
			return photo1;
		}

		public void setPhoto1(String photo1) {
			this.photo1 = photo1;
		}

		public String photo2;
		
		public String getPhoto2() {
			return photo2;
		}

		public void setPhoto2(String photo2) {
			this.photo2 = photo2;
		}

		public String photo3;

		public String getPhoto3() {
			return photo3;
		}

		public void setPhoto3(String photo3) {
			this.photo3 = photo3;
		}

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
		
		public Date begin_date;

		public Date getBegin_date() {
			return begin_date;
		}

		public void setBegin_date(Date begin_date) {
			this.begin_date = begin_date;
		}
		
		public Date end_date;

		public Date getEnd_date() {
			return end_date;
		}

		public void setEnd_date(Date end_date) {
			this.end_date = end_date;
		}


		public House(Long id_house,String description, double ratingsH, String services, String constraints, String photo1, String photo2, String photo3, Long id_user, String address, String city, int postal_code, String country, String additional_address, Date begin_date, Date end_date) {

			this.id_house = id_house;
			this.description = description;
			this.ratingsH = ratingsH;
			this.services = services;
			this.constraints = constraints;
			this.photo1 = photo1;
			this.photo2 = photo2;
			this.photo3 = photo3;
			this.id_user = id_user;
			this.address = address;
			this.city = city;
			this.postal_code = postal_code;
			this.country = country;
			this.additional_address = additional_address;
			this.begin_date = begin_date;
			this.end_date = end_date; 
		}
		
		public House() {}
}	