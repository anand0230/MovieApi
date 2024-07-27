package com.nt.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {


    private Integer movieId;
	
	private String title;
	
	private String  director;
	
	private Set<String> movieCast;
	
	private Integer releaseYear;
	
	private String poster;
	
	private String posterUrl;
	
}
