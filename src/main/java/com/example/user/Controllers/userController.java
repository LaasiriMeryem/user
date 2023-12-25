package com.example.user.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.entities.UserDTO;
import com.example.user.Services.userService;

@CrossOrigin("*")
@RestController
public class userController {

	@Autowired
	userService userService;
	
	@GetMapping("/users")
	public List<UserDTO> allUsers(){
		return userService.allUsersDTO();
	}
	
	@GetMapping("/users/{id}")
	public UserDTO getUerDtoById(@PathVariable String id) {
		return userService.getUserById(id);
	}
	
	@PutMapping("/users/{id}")
	public UserDTO updateUserDtoById(@PathVariable String id, @RequestBody UserDTO userDTO) {
		
		return userService.updateUserById(id, userDTO);
	}
	
	@DeleteMapping("/users/{id}")
	public boolean deleteUserDtoById(@PathVariable String id) {
		return userService.deleteUserById(id);
	}
	
	@PostMapping("/users")
	public UserDTO addUserDto(@RequestBody UserDTO userDTO) {
		return userService.createUser(userDTO);
	}
	
	
	
}
