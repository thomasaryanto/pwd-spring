package com.cimb.tokolapak.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;
import com.cimb.tokolapak.util.EmailUtil;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@PostMapping
	public User registerUser(@RequestBody User user) {
//		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
//		
//		if(findUser.toString() != "Optional.empty") {
//			throw new RuntimeException("Username already exist!");
//		}
		
		String encodedPassword = pwEncoder.encode(user.getPassword());
		String verifyToken = RandomString.make(20);
		
		user.setPassword(encodedPassword);
		user.setVerified(false);
		user.setVerifyToken(verifyToken);
		
		this.emailUtil.sendEmail(user.getEmail(), "Verifikasi Akun Kamu", "Silahkan verifikasi akun kamu dengan klik link dibawah ini : \n\n http://localhost:8080/users/verify/"+ verifyToken +"/");
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		return savedUser; 
	}
	
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) {
		User findUser = userRepo.findByUsername(user.getUsername()).get();
		
		if(pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}
		else {
			return null;
		}
	}
	
	//http://localhost:3001/login?username=xxx&password=xxx
	@GetMapping("/login")
	public User getLoginUser(@RequestParam String username, @RequestParam String password) {
		User findUser = userRepo.findByUsername(username).get();
		
		if(pwEncoder.matches(password, findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}
		else {
			return null;
		}
	}
	
	@PostMapping("/sendEmail")
	public String sendEmail() {
		this.emailUtil.sendEmail("thedevmango@gmail.com", "Test kirim email", "test email dari java spring \n\n tom");
		return "Email sent!";
	}
	
	@GetMapping("/verify/{token}")
	public String getLoginUser(@PathVariable String token) {
		User findUser;
		
		try {
			findUser = userRepo.findByVerifyToken(token).get();
		} catch (NoSuchElementException e) {
			return "Verify token not valid!";
		}
		
		if (findUser.isVerified()) {
			return "User "+ findUser.getUsername() +" already verified!";
		}
		else {
			findUser.setVerified(true);
			userRepo.save(findUser);		
			return "User "+ findUser.getUsername() +" successfully verified!";
		}
	}
}
