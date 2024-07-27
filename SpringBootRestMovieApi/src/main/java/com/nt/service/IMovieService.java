package com.nt.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nt.dto.MovieDto;

public interface IMovieService {
	
	
	public MovieDto addMovie(MovieDto movieDto,MultipartFile file) throws IOException;
	public MovieDto getMovie(Integer movieId);
	public List<MovieDto> getAllMovies();
	public String deleteMovie(Integer movieId) throws IOException;
	public String updateMovie(MovieDto movieDto,MultipartFile file) throws Exception;
	
	
	
}
