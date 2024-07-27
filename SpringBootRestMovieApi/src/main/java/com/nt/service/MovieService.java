package com.nt.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nt.dto.MovieDto;
import com.nt.entity.Movie;
import com.nt.exception.FileExistsException;
import com.nt.exception.MovieNotFoundException;
import com.nt.repository.IMovieRepository;

@Service
public class MovieService implements IMovieService {
	
	@Autowired
	private IMovieRepository movieRepo;
	
	@Autowired
	private IFileService fileService;
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	
	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
		

		//upload the file
		if(Files.exists(Paths.get(path+File.separator+file.getOriginalFilename()))) {
			throw new FileExistsException("file  is already exists! please provide other file");
		}
		String fileName=fileService.uploadFile(path, file);
		
		//set the value of field poster as file name
		movieDto.setPoster(fileName);
		
		//map moviedto to movie object
		Movie movie=new Movie(
				null,
				movieDto.getTitle(),
				movieDto.getDirector(),
				movieDto.getMovieCast(),
				movieDto.getReleaseYear(),
				movieDto.getPoster()
				);
		
		//save the movie object
		Movie savedMovie=movieRepo.save(movie);
		
		//generate the poster url
		String posterUrl=baseUrl+"/file/"+fileName;
		
		//map saved movie object to dto 
		MovieDto m=new MovieDto(
				savedMovie.getMovieId(),
				savedMovie.getTitle(),
				savedMovie.getDirector(),
				savedMovie.getMovieCast(),
				savedMovie.getReleaseYear(),
				savedMovie.getPoster(),
				posterUrl		
				);	
		
		return m;
	}//method

	@Override
	public MovieDto getMovie(Integer movieId) {
		//get the movie object
		Movie movie=movieRepo.findById(movieId)
				.orElseThrow(()->new MovieNotFoundException("movie is not found with the id= "+movieId));
		
		//create the posterUrl
		String posterUrl=baseUrl+"/file/"+movie.getPoster();
		
		
		
	    //map to movieDto
		
		MovieDto response=new MovieDto(
			
				movie.getMovieId(),
				movie.getTitle(),
				movie.getDirector(),
				movie.getMovieCast(),
				movie.getReleaseYear(),
				movie.getPoster(),
				posterUrl		
				
				);
		
		return response;
	}//method

	@Override
	public List<MovieDto> getAllMovies() {
		
		//get list of movie
		List<Movie> movieList=movieRepo.findAll();
		
		//converting that list to movieDto list with url
		List<MovieDto> movieDtos=new ArrayList<>();
		
		movieList.forEach(movie->{		
			String posterUrl=baseUrl+"/file/"+movie.getPoster();
			
			MovieDto moviedto=new MovieDto(
					
					movie.getMovieId(),
					movie.getTitle(),
					movie.getDirector(),
					movie.getMovieCast(),
					movie.getReleaseYear(),
					movie.getPoster(),
					posterUrl		
					
					);
			movieDtos.add(moviedto);
						
		});
	
		
			return movieDtos;
	}//method

	
	
	@Override
	public String deleteMovie(Integer movieId) throws IOException {
		
		//find Movie is available or not if available then delete 
		Movie movie=movieRepo.findById(movieId)
				.orElseThrow(()->new MovieNotFoundException("movie is not found with the id="+movieId));
		Integer id=movie.getMovieId();
			
		//if file is exists then delete
		Files.deleteIfExists(Paths.get(path+File.separator+movie.getPoster()));
		
		//delete movie object
		movieRepo.delete(movie);
			
		
		return "movie deleted with the id = "+ id;
	}//method
	
	
	

	@Override
	public String updateMovie(MovieDto movieDto, MultipartFile file) throws Exception {
		
		//find movie
		Movie movie=movieRepo.findById(movieDto.getMovieId())
				.orElseThrow(()->new MovieNotFoundException("movie is not found with the id"+movieDto.getMovieId()));
        
		
		
		//if file null then do nothing 
		//if file is not null then delete exists file 
		//and add new file
		String fileName=movie.getPoster();
		if(file!=null){	
	     Files.deleteIfExists(Paths.get(path+File.separator+fileName));
	     fileName=fileService.uploadFile(path, file);
		}
		
		
		//seting value in field name poster
		movieDto.setPoster(fileName);
		
		//map MovieDto data to movie object
		Movie updatedMovie=new Movie(
				movieDto.getMovieId(),
				movieDto.getTitle(),
				movieDto.getDirector(),
				movieDto.getMovieCast(),
				movieDto.getReleaseYear(),
				movieDto.getPoster()
				);
		
	   //save updated data to database
		movieRepo.save(updatedMovie);
			
		
		return "Movie Details Updated";
	}//method
	
	
	
	
}
