package PixelPhoenix.FireBNB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="constraint_table")
public class Constraint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_constraint", unique = true)
	Long id_constraint;

	public Long getId_constraint() {
		return id_constraint;
	}

	public void setId_constraint(Long id_constraint) {
		this.id_constraint = id_constraint;
	}
	
	String constraintName;

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	
	Long houseConstraint_FK;

	public Long getHouseConstraint_FK() {
		return houseConstraint_FK;
	}

	public void setHouseConstraint_FK(Long houseConstraint_FK) {
		this.houseConstraint_FK = houseConstraint_FK;
	}
	
	public Constraint(Long id_constraint, String constraintName) {
		this.id_constraint = id_constraint;
		this.constraintName = constraintName;
	}
	
	public Constraint(Long id_constraint, String constraintName, Long houseConstraint_FK) {
		this.id_constraint = id_constraint;
		this.constraintName = constraintName;
		this.houseConstraint_FK = houseConstraint_FK;
	}

	public Constraint() {}

}
