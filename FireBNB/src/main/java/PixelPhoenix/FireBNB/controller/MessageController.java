package PixelPhoenix.FireBNB.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		Iterable<Message> listUserMessages = messageService.getUserMessages(user.getId_user());
		
		model.addAttribute("listUserMessages", listUserMessages);
		model.addAttribute("loggedUser", user);
		return "messagesListUser";
	}
	
	@GetMapping("/messageProfile")public String messageProfile(Model model, @RequestParam("id_message") Long id_message) {
		
		Optional<Message> m = messageService.getMessage(id_message);
		Message message = m.get();
		Long idUser = message.getId_sender();
		Optional<User> u = us.getUserId(idUser);
		User sender = u.get();
		
		
		
		model.addAttribute("message", message);
		model.addAttribute("senderFirstName", sender.getFirstName());
		model.addAttribute("senderLastName", sender.getLastName());
		return "messageProfile";
	}
	
	@GetMapping("/messagesList/add")
	public String serviceForm(Model model) {
		model.addAttribute("message", new Message());
		return "addMessage";
	}

	@PostMapping("/messagesList/add")
	public String add(@ModelAttribute("message") @Validated Message message, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addMessage";
		}
		Message messageAdd = messageService.saveMessage(message);
		model.addAttribute("message", messageAdd);
		return "redirect:/messagesList";
	}
	
	@RequestMapping(value = "/messagesList/delete")
	public String delete(Model model, @RequestParam(name = "id_message", defaultValue = "") Long id_message,
			@RequestParam(name = "mc", defaultValue = "") String mc) {
		messageService.deleteMessage(id_message);
		return "redirect:/messagesList/search?motCle=" + mc;
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