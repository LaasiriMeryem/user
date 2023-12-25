package com.example.user.Services;

import com.example.user.Dao.*;
import com.example.user.entities.User;
import com.example.user.entities.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class userServiceTest {

	@Mock
    private userRepository userRepository;

	@InjectMocks
	private userService userService;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
    @Test
    public void testAllUsersDTO() {
        // Créez des utilisateurs fictifs pour le test
        User user1 = new User( "1","John", "Doe", "+123456789");
        User user2 = new User("2", "Jane", "Doe", "+987654321");

        // Définir le comportement simulé du repository
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Exécutez la méthode à tester
        List<UserDTO> result = userService.allUsersDTO();

        // Vérifiez le résultat à l'aide d'AssertJ
        assertThat(result).hasSize(2);

        // Vous pouvez ajouter plus d'assertions selon vos besoins
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(1).getLastName()).isEqualTo("Doe");
    }
    
    @Test
    public void testGetUserById() {
    	String userId="1";
    	User user=new User(userId);
    	user.setFirstName("meryem");
    	user.setLastName("laasiri");
    	user.setPhoneNumber("+33782292904");
    	
    	when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    	
    	UserDTO result=userService.getUserById(userId);
    	
    	assertNotNull(result);
    	assertThat(result.getFirstName().equals("meryem"));
    	assertThat(result.getLastName().equals("laasiri"));
    	assertThat(result.getPhoneNumber().equals("+33782292904"));
    	
    }
    
    @Test
    public void TestUpdateUserById() {
    	String userId="1";
    	UserDTO user=new UserDTO();
    	user.setId(userId);
    	user.setFirstName("meryem");
    	user.setLastName("laasiri");
    	user.setPhoneNumber("+33782292904");
    	
    	User existingUser=new User(userId);
    	existingUser.setFirstName("mery");
        existingUser.setLastName("benani");
        existingUser.setPhoneNumber("66666666666666");
        
    	
    	when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
    	when(userRepository.save(any(User.class))).thenReturn(existingUser);
    	
    	UserDTO result=userService.updateUserById(userId, user);
    	assertNotNull(result);
    	assertEquals(userId,result.getId());
    	assertThat(result.getFirstName().equals(user.getFirstName()));
    	assertThat(result.getLastName().equals(user.getLastName()));
    	assertThat(result.getPhoneNumber().equals(user.getPhoneNumber()));
    	
    	 // Verify that the repository method was called with the correct parameter
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Arrange
        String userId = "2";

        // Mocking repository behavior for a non-existent user
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        UserDTO result = userService.getUserById(userId);

        // Assert
        assertNull(result);

    }
    
    @Test
    public void testDeleteUserById() {
    	String userId="3";
    	User userDTO=new User();
    	userDTO.setId(userId);
    	userDTO.setFirstName("meryem");
    	userDTO.setLastName("laasiri");
    	userDTO.setPhoneNumber("00000000000");
    	
    	when(userRepository.findById(userId)).thenReturn(Optional.of(userDTO));
        
    	boolean result=userService.deleteUserById(userId);
    	
    	assertTrue(result);
    	
    	 verify(userRepository, times(1)).deleteById(userId);

    	
    	
    	
    	
    }
}
