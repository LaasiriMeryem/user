package com.example.user.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.Dao.userRepository;
import com.example.user.entities.User;
import com.example.user.entities.UserDTO;

@Service
public class userService {

	@Autowired
	userRepository userRepository;
	
	public UserDTO convertToUserDTO(User user) {
		UserDTO userDTO=new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setId(user.getId());
		
		return userDTO;
	}
	
	
	public List<UserDTO> allUsersDTO(){
		List<UserDTO> allUserDTO = new ArrayList<>();
		List<User> users=userRepository.findAll();
		if(!users.isEmpty()) {
		for(User user:users) {
			UserDTO userDTO=this.convertToUserDTO(user);
			allUserDTO.add(userDTO);
		}
		return allUserDTO;
		}
		
		return null;
	}
	
	  
	   public UserDTO getUserById(String userId) {
		    Optional<User> userOptional = userRepository.findById(userId);
		    return userOptional.map(this::convertToUserDTO).orElse(null);
		}
	   
	   public UserDTO updateUserById(String userId,UserDTO userDTO) {
		 User user=userRepository.findById(userId).get();
		 
		  if(user!=null) {
			  user.setId(userId);
			  user.setFirstName(userDTO.getFirstName());
			  user.setLastName(userDTO.getLastName());
			  user.setPhoneNumber(userDTO.getPhoneNumber());
			  userRepository.save(user);
			  return this.convertToUserDTO(user);
		  }
		   return null;
		   
	   }
	   
	   
	   public boolean deleteUserById(String userId) {
		   User user=userRepository.findById(userId).get();
			 
			  if(user!=null) {
				 userRepository.deleteById(userId);
				  return true;
			  }
			   return false;
	   }
	   
	   
	   public UserDTO createUser(UserDTO userDTO) {
		 User newUser = new User(userDTO.getId());
		 newUser.setFirstName(userDTO.getFirstName());
		 newUser.setLastName(userDTO.getLastName());
		 newUser.setPhoneNumber(userDTO.getPhoneNumber());
		 User saveUser=userRepository.save(newUser);
		 return this.convertToUserDTO(saveUser);
		 
	   }
	
}
