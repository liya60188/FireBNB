package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.repository.ConstraintRepository;

@Service
public class ConstraintService {
	
	@Autowired
	private ConstraintRepository constraintRepository;
	
	public Optional<Constraint> getConstraint(final Long id){
		return constraintRepository.findById(id);
	}
	
	public Iterable<Constraint> getConstraints(){
		return constraintRepository.findAll();
	}
 
	public void deleteConstraint(final Long id) {
		constraintRepository.deleteById(id);
    }
 
	 @PutMapping(value = "constraintsList")
	 public Constraint saveConstraint(@RequestBody Constraint constraint) {
		 Constraint savedConstraint = constraintRepository.save(constraint);
	     return savedConstraint;
	 }
	

}
