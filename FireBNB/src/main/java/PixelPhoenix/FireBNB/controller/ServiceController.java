package PixelPhoenix.FireBNB.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.service.ServiceService;

@Controller
public class ServiceController {
	@Autowired 
	private ServiceService serviceService;
	
	@GetMapping("/servicesList")
	public String listServices(Model model) {
		Iterable<Service> listServices = serviceService.getServices();
		model.addAttribute("listServices", listServices);
		return "servicesList";
	}
	
	@GetMapping("/servicesList/add")
	public String serviceForm(Model model) {
		model.addAttribute("service", new Service());
		return "addService";
	}

	@PostMapping("/servicesList/add")
	public String add(@ModelAttribute("service") @Validated Service service, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addService";
		}
		Service serviceAdd = serviceService.saveService(service);
		model.addAttribute("service", serviceAdd);
		return "redirect:/servicesList";
	}
	
	@RequestMapping(value = "/servicesList/delete")
	public String delete(Model model, @RequestParam(name = "id_service", defaultValue = "") Long id_service) {
		serviceService.deleteService(id_service);
		return "redirect:/servicesList";
	}
	
	// Doesn't work idk why
	@RequestMapping(value = "/servicesList/edit")
	public String edit(Model model, @RequestParam(name = "id_service", defaultValue = "") Long id_service,
			@RequestParam(name = "serviceName", defaultValue = "") String serviceName,
			@RequestParam(name = "houseService_FK", defaultValue = "") Long houseService_FK,
			@RequestParam(name = "edit", defaultValue = "") int edit) {
		if (edit == 0) {
			model.addAttribute("id_service", id_service);
			model.addAttribute("serviceName", serviceName);
			model.addAttribute("houseService_FK", houseService_FK);
			return "servicesEdit";
		} else {
			Optional<Service> os = serviceService.getService(id_service);
			Service service = os.get();
			service.setServiceName(serviceName);
			serviceService.saveService(service);
			return "redirect:/servicesList";
		}
	}
}
