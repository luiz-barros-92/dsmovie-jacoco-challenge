package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.DatabaseException;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private String title;
	private PageImpl<MovieEntity> page;
	private MovieEntity movie;
	private MovieDTO movieDTO;
	private Long existingMovieId, nonExistingMovieId, dependentId;	

	@BeforeEach
	void setUp() throws Exception {
		title = "The Witcher";
		movie = new MovieEntity(1L, title, 4.5, 2, "www.imagefromthisfilm.com");
		movieDTO = new MovieDTO(null, title, 5.0, 1, "www.thisfilm.com");
		page = new PageImpl<>(List.of(movie));
		existingMovieId = 1L;
		nonExistingMovieId = 1000L;
		dependentId = 2L;

		when(repository.searchByTitle(ArgumentMatchers.anyString(), (Pageable) ArgumentMatchers.any()))
				.thenReturn(page);

		when(repository.findById(existingMovieId)).thenReturn(Optional.of(movie));
		when(repository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
		
		when(repository.save(ArgumentMatchers.any())).thenReturn(movie);
		
		when(repository.getReferenceById(existingMovieId)).thenReturn(movie);		
		when(repository.getReferenceById(nonExistingMovieId)).thenThrow(EntityNotFoundException.class);
		
		when(repository.existsById(existingMovieId)).thenReturn(true);
		when(repository.existsById(nonExistingMovieId)).thenReturn(false);
		when(repository.existsById(dependentId)).thenReturn(true);
		
		doNothing().when(repository).deleteById(existingMovieId);		
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
	}

	@Test
	public void findAllShouldReturnPagedMovieDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<MovieDTO> result = service.findAll(title, pageable);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(result.getContent().get(0).getTitle(), title);

		verify(repository, times(1)).searchByTitle(title, pageable);
	}

	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.findById(existingMovieId);

		assertNotNull(result);
		assertEquals(existingMovieId, result.getId());
		verify(repository).findById(existingMovieId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingMovieId);
		});
	}

	@Test
	public void insertShouldReturnMovieDTO() {
		MovieDTO result = service.insert(movieDTO);
		
		assertNotNull(result);
		assertEquals(result.getId(), movie.getId());
		assertEquals(result.getTitle(), movie.getTitle());
	}

	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = service.update(existingMovieId, movieDTO);
		
		assertNotNull(result);
		assertEquals(existingMovieId, result.getId());
		assertEquals(movie.getTitle(), result.getTitle());
		
		verify(repository).getReferenceById(existingMovieId);
		verify(repository).save(ArgumentMatchers.any());
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> {
	        service.update(nonExistingMovieId, movieDTO);
	    });
		
		verify(repository).getReferenceById(nonExistingMovieId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		assertDoesNotThrow(() -> {
	        service.delete(existingMovieId);
	    });
		
		verify(repository, times(1)).deleteById(existingMovieId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> {
	        service.delete(nonExistingMovieId);	        
	    });
		
		verify(repository, never()).deleteById(nonExistingMovieId);
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {		
		assertThrows(DatabaseException.class, () -> {
	        service.delete(dependentId);
	    });
		
		verify(repository).deleteById(dependentId);
	}
}
