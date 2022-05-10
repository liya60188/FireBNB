package PixelPhoenix.FireBNB.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import PixelPhoenix.FireBNB.model.Photo;
import PixelPhoenix.FireBNB.service.PhotoService;

@Controller
public class PhotoController {

	@Autowired
	private PhotoService photoService;
	
	@GetMapping("/photosList")
	public String listPhotos(Model model) {
		Iterable<Photo> listPhotos = photoService.getPhotos();
		model.addAttribute("listPhotos", listPhotos);
		return "photosList";
	}
	
	@PostMapping("/photosList/add")
    public String saveProduct(@RequestParam("file") MultipartFile file)
    {
		photoService.savePhoto(file);
    	return "redirect:/listProducts.html";
    }
	
	@RequestMapping(value = "/photosList/delete")
	public String delete(Model model, @RequestParam(name = "id_photo", defaultValue = "") Long id_photo,
			@RequestParam(name = "mc", defaultValue = "") String mc) {
		photoService.deletePhoto(id_photo);
		return "redirect:/photosList/search?motCle=" + mc;
	}
}
