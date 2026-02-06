package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;
	
	@Mock
    private MovieRepository movieRepository;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private UserService userService;
    
    private Long existingMovieId;
    private Long nonExistingMovieId;
    private MovieEntity movie;
    private UserEntity user;
    private ScoreDTO scoreDTO;
    private ScoreEntity score;
    
    @BeforeEach
    void setUp() throws Exception {
        existingMovieId = 1L;
        nonExistingMovieId = 2L;
        
        user = new UserEntity(1L, "Maria", "maria@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
        movie = new MovieEntity(existingMovieId, "Rosemary's Baby", 4.5, 2, "www.imagefromthisfilm.com");
        scoreDTO = new ScoreDTO(existingMovieId, 5.0);
        
        score = new ScoreEntity();
        score.setMovie(movie);
        score.setUser(user);
        score.setValue(scoreDTO.getScore());
        
        when(userService.authenticated()).thenReturn(user);
        when(scoreRepository.saveAndFlush(ArgumentMatchers.any())).thenReturn(score);
        
        when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movie));
        when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
        
        when(movieRepository.save(ArgumentMatchers.any())).thenReturn(movie);
        
    }
	
	@Test
	public void saveScoreShouldReturnMovieDTO() {
		MovieDTO result = service.saveScore(scoreDTO);
		
		assertNotNull(result);
		assertEquals(result.getId(), existingMovieId);
		
		verify(scoreRepository).saveAndFlush(ArgumentMatchers.any());
		verify(movieRepository).save(movie);
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		scoreDTO = new ScoreDTO(nonExistingMovieId, 5.0);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            service.saveScore(scoreDTO);
        });
		
		verify(movieRepository).findById(nonExistingMovieId);
		verify(scoreRepository, Mockito.never()).saveAndFlush(ArgumentMatchers.any());
	}
}
