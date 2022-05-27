package PixelPhoenix.FireBNB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.repository.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;
	
	
	public Optional<Message> getMessage(final Long id){
		return messageRepository.findById(id);
	}
	
	public Iterable<Message> getMessages(){
		return messageRepository.findAll();
	}
	
	public Iterable<Message> getUserMessages(Long id_user){
		return messageRepository.findListMessages(id_user);
	}
 
	public void deleteMessage(final Long id) {
		messageRepository.deleteById(id);
    }
 
	 @PutMapping(value = "messagesList")
	 public Message saveMessage(@RequestBody Message message) {
		 Message savedMessage = messageRepository.save(message);
	     return savedMessage;
	 }

	public Page<Message> getMessageBySearch(String mc, int page, int size) {
		return messageRepository.findByName("%" + mc + "%", PageRequest.of(page, size));
	}
}