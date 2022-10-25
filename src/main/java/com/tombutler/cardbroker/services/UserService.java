package com.tombutler.cardbroker.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tombutler.cardbroker.models.LoginUser;
import com.tombutler.cardbroker.models.User;
import com.tombutler.cardbroker.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public void validate(User newUser, BindingResult result) {
		if(!newUser.getPassword().equals(newUser.getConfirmPass())) {
			result.rejectValue("confirm", "Mismatch", "Passwords do not match");
		}
		if(userRepo.findByEmail(newUser.getEmail())!=null) {
			result.rejectValue("email", "Duplicate", "Email already taken");
		}
	}
	
	public User registerUser(User newUser) {
		String hashedPas = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		newUser.setConfirmPass(hashedPas);
		userRepo.save(newUser);
		return null;
	}
	
	public User findById(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	public boolean authenticateUser(LoginUser newLogin, BindingResult result) {
		User user = userRepo.findByEmail(newLogin.getEmail());
		if(user==null) {
			result.rejectValue("email", "notRegistered", "Email not found. Please register to login");
			return false;
		} else {
			if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
				result.rejectValue("password", "incorrectPass", "Incorrect password");
				return false;
			}
		}
		return true;
	}
}
