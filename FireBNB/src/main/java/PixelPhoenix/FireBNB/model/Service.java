package PixelPhoenix.FireBNB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service")
public class Service{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_service", unique = true)
	Long id_service;
	
	public Long getId_service() {
		return id_service;
	}

	public void setId_service(Long id_service) {
		this.id_service = id_service;
	}

	String serviceName;
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	// Need constructor with 2 argmts to not have an error while joining for HouseServices
	public Service(Long id_service, String serviceName) {
		this.id_service = id_service;
		this.serviceName = serviceName;
	}

	public Service() {}
}