package PixelPhoenix.FireBNB.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import PixelPhoenix.FireBNB.model.Photo;
import PixelPhoenix.FireBNB.repository.PhotoRepository;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepository;
	
	public Optional<Photo> getPhoto(final Long id){
		return photoRepository.findById(id);
	}
	
	public Iterable<Photo> getPhotos(){
		return photoRepository.findAll();
	}
	
	public void deletePhoto(final Long id) {
		photoRepository.deleteById(id);
    }
	
	
	//to save a photo in the database
	public void savePhoto(MultipartFile file) {
		Photo p = new Photo();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			p.setHousePhoto(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		photoRepository.save(p);
	}
	
	
	
}
