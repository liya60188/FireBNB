package PixelPhoenix.FireBNB;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import PixelPhoenix.FireBNB.controller.UserController;
import PixelPhoenix.FireBNB.model.User;
import PixelPhoenix.FireBNB.repository.UserRepository;
import PixelPhoenix.FireBNB.service.UserService;

@SpringBootApplication
public class FireBnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireBnbApplication.class, args);

	}

}
