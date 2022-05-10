package PixelPhoenix.FireBNB.model;

import java.util.List;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@ManyToMany(mappedBy="roles")
    private List<User> user;
	    
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers(){
		return user;
	}
	public void setUsers(List<User> user, Integer id, String name) {
		this.id = id;
		this.name = name;
		this.user = user;
	}
	    
	public Role() {
		
	}
	
	public Role(String name) {
		this.name = name;
	}
}
