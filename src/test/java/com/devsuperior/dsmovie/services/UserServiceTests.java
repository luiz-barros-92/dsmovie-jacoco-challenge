package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.projections.UserDetailsProjection;
import com.devsuperior.dsmovie.repositories.UserRepository;
import com.devsuperior.dsmovie.utils.CustomUserUtil;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
    private CustomUserUtil userUtil;
	
	private String existingUsername, nonExistingUsername;
	private UserEntity user;	
	private List<UserDetailsProjection> userDetails;
	
	@BeforeEach
	void setup() throws Exception {
		existingUsername = "maria@gmail.com";
		nonExistingUsername = "john@gmail.com";		
		user = new UserEntity(1L, "maria", existingUsername, "1234");
	}

	@Test
	public void authenticatedShouldReturnUserEntityWhenUserExists() {
		when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
		when(repository.findByUsername(existingUsername)).thenReturn(Optional.of(user));
		
		UserEntity result = service.authenticated();
		
		assertNotNull(result);
		assertEquals(existingUsername, result.getUsername());
		assertEquals(user.getId(), result.getId());
		
		verify(repository).findByUsername(existingUsername);
	}

	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		when(userUtil.getLoggedUsername()).thenReturn(nonExistingUsername);
		when(repository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());
		
		assertThrows(UsernameNotFoundException.class, () -> {
	        service.authenticated();
	    });
	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
	}

	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
	}
}
