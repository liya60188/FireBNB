package PixelPhoenix.FireBNB.model;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import PixelPhoenix.FireBNB.service.UserService;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
@Table(name="messaging")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_message", unique = true)
	Long id_message;
	Long id_sender;
	Long id_receiver;
	Long id_house_receiver;
	Long id_house_sender;
	String subject;
	String content;
	Date creation_date;
	
	public Long getId_message() {
		return id_message;
	}

	public void setId_message(Long id_message) {
		this.id_message = id_message;
	}

	public Long getId_sender() {
		return id_sender;
	}

	public void setId_sender(Long id_sender) {
		this.id_sender = id_sender;
	}

	public Long getId_receiver() {
		return id_receiver;
	}

	public void setId_receiver(Long id_receiver) {
		this.id_receiver = id_receiver;
	}

	
	public Long getId_house_receiver() {
		return id_house_receiver;
	}

	public void setId_house_receiver(Long id_house_receiver) {
		this.id_house_receiver = id_house_receiver;
	}
	public Long getId_house_sender() {
		return id_house_sender;
	}

	public void setId_house_sender(Long id_house_sender) {
		this.id_house_sender = id_house_sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	@PrePersist
	private void onCreate() {

	    creation_date = new Date();
	}
	
//	@Autowired
//	UserService us;
//	public String getSenderFullName(Long id_sender) {
//		Optional<User> u = us.getUserId(id_sender);
//		User user = u.get();
//		return user.getFirstName() + ' ' + user.getLastName();
//		
//	}

	public Message(Long id_message, Long id_sender, Long id_receiver, Long id_house_receiver, Long id_house_sender, String subject, String content, Date creation_date) {
		this.id_message = id_message;
		this.id_sender = id_sender;
		this.id_receiver = id_receiver;
		this.id_house_receiver = id_house_receiver;
		this.id_house_sender = id_house_sender;
		this.subject = subject;
		this.content = content;
		this.creation_date = creation_date;
		
	}
	
	public Message() {}
}