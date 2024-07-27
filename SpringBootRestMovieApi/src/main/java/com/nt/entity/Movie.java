package com.nt.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer movieId;
	
    @Column(nullable=false)
	private String title;
	
    @Column(nullable=false)
	private String  director;
	
	@ElementCollection
	private Set<String> movieCast;
	
   
	private Integer releaseYear;
	
    @Column(nullable=false)
	private String poster;
	
	
}
