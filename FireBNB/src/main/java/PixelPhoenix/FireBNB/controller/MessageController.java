package PixelPhoenix.FireBNB.controller;

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

import PixelPhoenix.FireBNB.model.Message;
import PixelPhoenix.FireBNB.service.MessageService;

@Controller
public class MessageController {
	@Autowired 
	private MessageService messageService;
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
}