package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PixelPhoenix.FireBNB.repository.ConstraintRepository;

@Service
public class ConstraintService {
	
	@Autowired
	private ConstraintRepository constraintRepository;
	
	public Optional<PixelPhoenix.FireBNB.model.Constraint> getConstraint(final Long id){
		return constraintRepository.findById(id);
	}
	
	public Iterable<PixelPhoenix.FireBNB.model.Constraint> getConstraints(){
		return constraintRepository.findAll();
	}
 
	public void deleteConstraint(final Long id) {
		constraintRepository.deleteById(id);
    }
 
	 @PutMapping(value = "servicesList")
	 public PixelPhoenix.FireBNB.model.Constraint saveConstraint(@RequestBody PixelPhoenix.FireBNB.model.Constraint constraint) {
		 PixelPhoenix.FireBNB.model.Constraint savedConstraint = constraintRepository.save(constraint);
	     return savedConstraint;
	 }
	

}
