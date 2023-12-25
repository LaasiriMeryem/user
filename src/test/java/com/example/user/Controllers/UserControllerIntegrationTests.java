package com.example.user.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.user.entities.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private userController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", "65889b63597daf29427e7f12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("65889b63597daf29427e7f12")));
    }
    
    @Test
    public void testAddUserDto() throws Exception {
        // Créez un objet UserDTO à envoyer dans le corps de la requête
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setPhoneNumber("123456789");

        // Effectuez une requête POST avec MockMvc
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()) // Assurez-vous que l'ID est généré
                .andExpect(jsonPath("$.firstName").value("John")) // Assurez-vous que les champs correspondent
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"));
    }
    
    @Test
    public void testUpdateUserDtoById() throws Exception {
        // Créez un objet UserDTO à envoyer dans le corps de la requête
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("UpdatedFirstName");
        userDTO.setLastName("UpdatedLastName");
        userDTO.setPhoneNumber("987654321");

        // Effectuez une requête PUT avec MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", "65889b63597daf29427e7f12")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("65889b63597daf29427e7f12")) // Assurez-vous que l'ID reste le même
                .andExpect(jsonPath("$.firstName").value("UpdatedFirstName")) // Assurez-vous que les champs sont mis à jour
                .andExpect(jsonPath("$.lastName").value("UpdatedLastName"))
                .andExpect(jsonPath("$.phoneNumber").value("987654321"));
    }
    
    @Test
	public void testDeleteUserDtoById() throws Exception {
	    // Effectuez une requête DELETE avec MockMvc
	    mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", "65889b63597daf29427e7f12"))
	            .andExpect(status().isOk());
	    
	 	}
}
