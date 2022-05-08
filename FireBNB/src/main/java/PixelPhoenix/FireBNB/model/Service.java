package PixelPhoenix.FireBNB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service")
public class Service {
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
	
	Long houseService_FK;	
	public Long getHouseService_FK() {
		return houseService_FK;
	}

	public void setHouseService_FK(Long houseService_FK) {
		this.houseService_FK = houseService_FK;
	}
	
	// Need constructor with 2 argmts to not have an error while joining for HouseServices
	public Service(Long id_service, String serviceName) {
		this.id_service = id_service;
		this.serviceName = serviceName;
	}

	public Service(Long id_service, String serviceName, Long houseService_FK) {
		this.id_service = id_service;
		this.serviceName = serviceName;
		this.houseService_FK = houseService_FK;
	}

	public Service() {}
}
