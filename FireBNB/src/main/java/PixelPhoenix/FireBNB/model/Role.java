package PixelPhoenix.FireBNB.model;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name="roles2")
public class Role {
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	    
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Role(String name) {
		super();
		this.name = name;
	}
	
	public Role() {
		
	}
}