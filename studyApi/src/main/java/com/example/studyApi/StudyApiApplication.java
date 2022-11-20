package com.example.studyApi;

import com.example.studyApi.dto.SignUpDTO;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.service.AccountService;
import com.example.studyApi.service.SettingsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StudyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyApiApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(AccountService accountService, SettingsService settingsService){
		return args -> {
			SignUpDTO signUpDTO = new SignUpDTO();
			signUpDTO.setPassword("1234");
			signUpDTO.setNickname("test1");
			signUpDTO.setEmail("syseoz@naver.com");

			accountService.saveUser(signUpDTO);
		};
	}

}
