package PixelPhoenix.FireBNB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={
		"PixelPhoenix.FireBNB.controller", "PixelPhoenix.FireBNB.model",
"PixelPhoenix.FireBNB.repository", "PixelPhoenix.FireBNB.service"})
public class FireBnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireBnbApplication.class, args);
	}

}
