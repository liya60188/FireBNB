package PixelPhoenix.FireBNB.controller;

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

import PixelPhoenix.FireBNB.model.Constraint;
import PixelPhoenix.FireBNB.model.Service;
import PixelPhoenix.FireBNB.repository.ConstraintRepository;
import PixelPhoenix.FireBNB.repository.ServiceRepository;
import PixelPhoenix.FireBNB.service.ConstraintService;

@Controller
public class ConstraintController {
	@Autowired
	private ConstraintService constraintService;
	
	/*
	@GetMapping("/constraintsList")
	public String listConstraints(Model model) {
		Iterable<Constraint> listConstraints = constraintService.getConstraints();
		model.addAttribute("listConstraints", listConstraints);
		return "constraintsList";
	}
	*/
	
	
	//List of constraints Page with search bar
		@Autowired
		private ConstraintRepository constraintRepository;
		@RequestMapping(value = {"/constraintsList", "/constraintsList/search"})
		public String search(Model model, @RequestParam(name = "motCle", defaultValue = "") String mc,
				@RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "5") int size) {
			Page<Constraint> listConstraints = constraintRepository.findByName("%" + mc + "%", PageRequest.of(page, size));
			int[] pages = new int[listConstraints.getTotalPages()];
			model.addAttribute("listConstraints", listConstraints.getContent());
			model.addAttribute("motC", mc);
			model.addAttribute("pages", pages);
			model.addAttribute("pageCourante", page);
			return "constraintsList";
		}
	
	
	@GetMapping("/constraintsList/add")
	public String constraintForm(Model model) {
		model.addAttribute("constraint", new Constraint());
		return "addConstraint";
	}
	
	@PostMapping("/constraintsList/add")
	public String add(@ModelAttribute("constraint") @Validated Constraint constraint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addConstraint";
		}
		Constraint constraintAdd = constraintService.saveConstraint(constraint);
		model.addAttribute("constraint", constraintAdd);
		return "redirect:/constraintsList";
	}
	
	@RequestMapping(value = "/constraintsList/delete")
	public String delete(Model model, @RequestParam(name = "id_constraint", defaultValue = "") Long id_constraint) {
		constraintService.deleteConstraint(id_constraint);
		return "redirect:/constraintsList";
	}
	
	@RequestMapping(value = "/listConstraints/edit")
	public String edit(Model model, @RequestParam(name = "id_constraint", defaultValue = "") Long id_constraint,
			@RequestParam(name = "constraintName", defaultValue = "") String constraintName,
			@RequestParam(name = "houseConstraint_FK", defaultValue = "") Long houseConstraint_FK,
			@RequestParam(name = "edit", defaultValue = "") int edit) {
		if (edit == 0) {
			model.addAttribute("id_constraint", id_constraint);
			model.addAttribute("constraintName", constraintName);
			model.addAttribute("houseConstraint_FK", houseConstraint_FK);
			return "constraintsEdit";
		} else {
			Optional<Constraint> os = constraintService.getConstraint(id_constraint);
			Constraint constraint = os.get();
			constraint.setConstraintName(constraintName);
			constraintService.saveConstraint(constraint);
			return "redirect:/constraintsList";
		}
	}
}
