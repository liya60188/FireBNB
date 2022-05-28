package PixelPhoenix.FireBNB.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5978500248036655362L;
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	public double rating;
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
	private String role;
	@Column(name="profile_picture", nullable = true, length = 64)
    private String profilePicture;
	private String description;

	public Long getId_user() {
		return user_id;
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

	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	@PrePersist
	private void onCreate() {
		this.rating = 0.0;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.role = profilePicture;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public User(Long user_id, String firstName, String lastName, double rating, String email, String password,
			String address, String city, int postalCode, String country, String additionalAddress, 
			int phoneNumber, String profilePicture, String role, String description) {
		//super();
		this.user_id = user_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.additionalAddress = additionalAddress;
		this.phoneNumber = phoneNumber;
		this.profilePicture = profilePicture;
		this.role = role;
		this.description = description;
	}
	
	public User() {
	}

}