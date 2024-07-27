package com.nt.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nt.service.IFileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private IFileService fis;
	
	@Value("${project.poster}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file)throws IOException{
		
       String uploadedFileName=fis.uploadFile(path, file);
       System.out.println("yes file uploaded");
       
		return new ResponseEntity<String>("File UPloaded: "+uploadedFileName,HttpStatus.OK) ;
	}//method
	
	@GetMapping("{fileName}")
	public void servFileHandler(@PathVariable String fileName,HttpServletResponse response)throws IOException {
		
		InputStream resourceFile=fis.getResourceFile(path, fileName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceFile, response.getOutputStream());
		System.out.println("yes file fetched from here");
		
	}//method
	
	
}
