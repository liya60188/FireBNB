package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PixelPhoenix.FireBNB.model.House;
import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.model.Rating;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.service.HouseService;
import PixelPhoenix.FireBNB.service.MessageService;
import PixelPhoenix.FireBNB.service.UserService;

@Controller
public class MessageController {
	@Autowired 
	private MessageService messageService;
	@Autowired
	private UserService us;
	@Autowired 
	private HouseService hs;
	
	@RequestMapping(value = {"/messagesList", "/messagesList/search"})
	public String search(Model model, @RequestParam(name = "motCle", defaultValue = "") String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Page<Message> listMessages = messageService.getMessageBySearch(mc, page, size);
		int[] pages = new int[listMessages.getTotalPages()];
		model.addAttribute("listMessages", listMessages.getContent());
		model.addAttribute("motC", mc);
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", page);
		return "messagesList";
	}
	
	@RequestMapping("/messagesListUser")
	public String userMessages(Model model, Principal principal) {
		String emailLoggedUser = principal.getName();
		User user = us.getUser(emailLoggedUser);
		Long id_loggedUser = user.getId_user();
		Iterable<Message> listReceivedMessages = messageService.getReceivedMessages(user.getId_user());
		Iterable<Message> listSentMessages = messageService.getSentMessages(user.getId_user());
		Hashtable<Long,String> listReceivedNames = new Hashtable<Long, String>();
		
		for (Message message : listSentMessages) {

			Optional<User> u = us.getUserId(message.getId_receiver());
			User userReceiver = u.get();
			listReceivedNames.put(message.getId_receiver(), userReceiver.getFirstName() + ' ' + userReceiver.getLastName());
		}
		
		model.addAttribute("listReceivedMessages", listReceivedMessages);
		model.addAttribute("listSentMessages", listSentMessages);
		model.addAttribute("listReceivedNames",listReceivedNames);
		model.addAttribute("loggedUser", user);
		return "messagesListUser";
	}
	
	@GetMapping("/messageProfile")
	public String messageProfile(Model model, @RequestParam("id_message") Long id_message) {
		
		Optional<Message> m = messageService.getMessage(id_message);
		Message message = m.get();
		Long idUser = message.getId_sender();
		Optional<User> u = us.getUserId(idUser);
		User sender = u.get();
		
		model.addAttribute("message", message);
		model.addAttribute("senderFirstName", sender.getFirstName());
		model.addAttribute("senderLastName", sender.getLastName());
		model.addAttribute("email", sender.getEmail());
		return "messageProfile";
	}
	
	@GetMapping("/messagesListUser/add")
	public String sendMessageAdmin(Model model, Principal principal) {
		String emailLoggedUser = principal.getName();
		User user = us.getUser(emailLoggedUser);
		Long idLoggedUser = user.getId_user();
		
		//model.addAttribute("message", new Message());
		model.addAttribute("id_loggedUser", idLoggedUser);
		model.addAttribute("loggedUser", user);
		return "addMessage";
	}
	
	@GetMapping("/messagesListUser/sendMessage")
	public String sendMessageUser(Model model, Principal principal, 
			@RequestParam("email") String emailUser,
			@RequestParam("subject") String subject) {
		
		String emailLoggedUser = principal.getName();
		User loggedUser = us.getUser(emailLoggedUser);
		Long idLoggedUser = loggedUser.getId_user();
		
		User user = us.getUser(emailUser);
		Long idUser = user.getId_user();
		
		model.addAttribute("id_loggedUser", idLoggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("user", user);
		model.addAttribute("id_user", idUser);
		model.addAttribute("subject", subject);
		return "sendMessageUser";
	}

	@PostMapping("/messagesListUser/sendMessage")
	public String sendMessages(Model model,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("id_sender") Long id_sender,
			@RequestParam("id_receiver") Long id_receiver) {

			Message message = new Message();
			message.setId_sender(id_sender);
			message.setSubject(subject);
			message.setContent(content);
		    message.setId_receiver(id_receiver);
		    messageService.saveMessage(message);


		return "redirect:/messagesListUser";
	}
	@PostMapping("/messagesListUser/add")
	public String add(Model model,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("id_sender") Long id_sender) {

		Iterable<User> adminList = us.getAdmins();
		for (User a : adminList) {
			Long id_admin = a.getId_user();
			Message message = new Message();
			message.setId_sender(id_sender);
			message.setSubject(subject);
			message.setContent(content);
		    message.setId_receiver(id_admin);
		    messageService.saveMessage(message);
		}

		return "redirect:/messagesListUser";
	}
	
	@RequestMapping(value = "/messagesList/delete")
	public String delete(Model model, @RequestParam(name = "id_message", defaultValue = "") Long id_message,
			@RequestParam(name = "mc", defaultValue = "") String mc) {
		messageService.deleteMessage(id_message);
		return "redirect:/messagesListUser";
	}
	
	@PostMapping("/confirmBooking")
	public String confirmBooking(Model model, Principal principal, @RequestParam("confirm") String confirm, @RequestParam("id_house_receiver") Long id_house_receiver, @RequestParam("id_house_sender") Long id_house_sender) throws ParseException {
		Optional<House> ot = hs.getHouse(id_house_sender);
		House house = ot.get();
		Long id_sender = house.getId_user();
		

		Date begin_date = house.getBegin_date();
		Date end_date = house.getEnd_date();
		
		String emailLoggedUser = principal.getName();
		User loggedUser = us.getUser(emailLoggedUser);
		
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		Date dateReset = s.parse("0000-00-00");
		
		if(confirm.equals("no")) {
			house.setBegin_date(dateReset);
			house.setEnd_date(dateReset);
			
			Message message = new Message();
			message.setId_sender(loggedUser.getId_user());
			message.setId_receiver(id_sender);
			message.setSubject(house.getAddress() + ", " + house.getCity() + ", " + house.getCountry());
			message.setContent(loggedUser.getFirstName()+" "+loggedUser.getLastName()+" has declined the offer.");
			message.setId_house_receiver(id_house_receiver);
			messageService.saveMessage(message);
		}else{
			Message message = new Message();
			message.setId_sender(loggedUser.getId_user());
			message.setId_receiver(id_sender);
			message.setSubject(house.getAddress() + ", " + house.getCity() + ", " + house.getCountry());
			message.setContent(loggedUser.getFirstName()+" "+loggedUser.getLastName()+" has accepted your offer. The reservation has been made");
			message.setId_house_receiver(id_house_receiver);
			messageService.saveMessage(message);
		}
		
		return "redirect:/messagesListUser";
		
	}
}