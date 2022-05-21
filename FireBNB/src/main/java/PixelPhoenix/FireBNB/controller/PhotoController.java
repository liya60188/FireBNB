package PixelPhoenix.FireBNB.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

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
		//photoService.savePhoto(file);
    	return "redirect:/photosList";
    }
	
	@RequestMapping(value = "/photosList/delete")
	public String delete(Model model, @RequestParam(name = "id_photo", defaultValue = "") Long id_photo,
			@RequestParam(name = "mc", defaultValue = "") String mc) {
		photoService.deletePhoto(id_photo);
		return "redirect:/photosList/search?motCle=" + mc;
	}
	/*
	 @PostMapping("/photosList/add")
	    public RedirectView saveUser(@ModelAttribute("photo") @Validated Photo photo, Model model, 
	            @RequestParam("image") MultipartFile multipartFile) throws IOException {
	         
		 	photoService.createPhoto(photo);
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        //model.setHousePhoto(fileName);
	         
	        Photo savedPhoto= photoService.savePhoto(photo);
	 
	        String uploadDir = "house_photos/" + savedPhoto.getId_photo();
	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	         
	        return new RedirectView("/photosList", true);
	    }
	    */
	    
}
