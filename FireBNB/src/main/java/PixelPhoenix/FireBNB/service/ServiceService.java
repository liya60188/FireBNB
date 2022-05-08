package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PixelPhoenix.FireBNB.repository.ServiceRepository;

@Service
public class ServiceService {
	@Autowired
	 private ServiceRepository serviceRepository;

	 public Optional<PixelPhoenix.FireBNB.model.Service> getService(final Long id){
			return serviceRepository.findById(id);
		}
		
	 public Iterable<PixelPhoenix.FireBNB.model.Service> getServices(){
			return serviceRepository.findAll();
		}
	 
	 public void deleteService(final Long id) {
		 serviceRepository.deleteById(id);
	    }
	 
	 @PutMapping(value = "servicesList")
	 public PixelPhoenix.FireBNB.model.Service saveService(@RequestBody PixelPhoenix.FireBNB.model.Service service) {
		 PixelPhoenix.FireBNB.model.Service savedService = serviceRepository.save(service);
         return savedService;
	 }
	   
}
