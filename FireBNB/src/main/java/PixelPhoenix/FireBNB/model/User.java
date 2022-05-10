package PixelPhoenix.FireBNB.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_user", unique = true)
	private Long id_user;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	private int age;
	private int rating;
	private String email;
	private String password;
	private String address;
	private String city;
	@Column(name="postal_code")
	private int postalCode;
	private String country;
	@Column(name="additional_address")
	private String additionalAddress;
	@Column(name="phone_number")
	private int phoneNumber;
    @ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_role")
    private List<Role> roles;
	

	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getAdditionalAddress() {
		return additionalAddress;
	}
	public void setAdditionalAddress(String additionalAddress) {
		this.additionalAddress = additionalAddress;
	}
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public User(Long id_user, String firstName, String lastName, int age, int rating, String email, String password,
			String address, String city, int postalCode, String country, String additionalAddress, int phoneNumber) {
		super();
		this.id_user = id_user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.rating = rating;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.additionalAddress = additionalAddress;
		this.phoneNumber = phoneNumber;
	}
	
	public User() {
	}

}
