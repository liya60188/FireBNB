package PixelPhoenix.FireBNB.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.service.ServiceService;

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

		
		// TODO - Create FK in db
//		@OneToMany(targetEntity = Service.class, cascade = CascadeType.ALL)
//		@JoinColumn(name = "houseService_FK", referencedColumnName = "id_house")
//		
//		Set<Service> services;
//		public Set<Service> getServices() {
//			return services;
//		}
//
//		public void setServices(Set<Service> services) {
//			this.services = services;
//		}
//		
//		//Constraints
//		@OneToMany(targetEntity = Constraint.class, cascade = CascadeType.ALL)
//		@JoinColumn(name = "houseConstraint_FK", referencedColumnName = "id_house")
//		Set<Constraint> constraints;
//		public Set<Constraint> getConstraints() {
//			return constraints;
//		}
//
//		public void setConstraints(Set<Constraint> constraints) {
//			this.constraints = constraints;
//		}
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
		Long id_user;
		
		public Long getId_user() {
			return id_user;
		}

		public void setId_user(Long id_user) {
			this.id_user = id_user;
		}
		
		public House(Long id_house,String description, int ratingsH, String services, String constraints,String photos, Long id_user) {
			this.id_house = id_house;
			this.description = description;
			this.ratingsH = ratingsH;
			this.services = services;
			this.constraints = constraints;
			this.photos = photos;	
			this.id_user = id_user;
		}
		
		public House() {}
}
