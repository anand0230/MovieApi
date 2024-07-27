package com.nt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.dto.MovieDto;
import com.nt.exception.EmptyFileException;
import com.nt.service.IMovieService;

@RestController
@RequestMapping("/movie/api")
public class MovieController {
	
	@Autowired
	private IMovieService movieService;

	
	@PostMapping("/add-movie")
	public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException{
		
		if(file.isEmpty()) {
		    throw new EmptyFileException("File is Empty");
		}
		//convert json to movieDto
		MovieDto dto =convertMovieDtoToObject(movieDto);
		return new ResponseEntity<MovieDto>(movieService.addMovie(dto, file),HttpStatus.CREATED);
	}//method
	
	
	
	@GetMapping("/findByid/{id}")
	public ResponseEntity<MovieDto> getMovie(@PathVariable Integer id){	
		return new ResponseEntity<MovieDto>(movieService.getMovie(id),HttpStatus.OK);	
	}//method
	
	
	
	@GetMapping("/all")
	public ResponseEntity<Object> showAll(){	
		return new ResponseEntity<Object>(movieService.getAllMovies(),HttpStatus.OK);
	}//method
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable Integer id) throws IOException{
		return new ResponseEntity<String>(movieService.deleteMovie(id),HttpStatus.OK);
	}//method
	
	
	
	
	@PutMapping("/update-movie")
	public ResponseEntity<String> updateMovie(@RequestPart MultipartFile file,@RequestPart String movieDto) throws Exception{
		//if file is not there make it null
		if(file.isEmpty()) file=null;
		
		//convert json to movieDto
		MovieDto dto =convertMovieDtoToObject(movieDto);
		
		return new ResponseEntity<String>(movieService.updateMovie(dto, file),HttpStatus.CREATED);
	}//method



	private MovieDto convertMovieDtoToObject(String movieDto) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		MovieDto dto =mapper.readValue(movieDto, MovieDto.class);
		return dto;
	}
	
	
	
	
}
