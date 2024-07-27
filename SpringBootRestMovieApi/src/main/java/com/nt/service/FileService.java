package com.nt.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements IFileService{

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		//getName of the file
		String  fileName=file.getOriginalFilename();
		
		//toget the file path
		String filePath =path+File.separator+fileName;
		
		//create a file oJbect 
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//copy the file or upload file to the path
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		return fileName;
		
	}//method

	@Override
	public InputStream getResourceFile(String path, String fileName) throws IOException {
		
		String filePath=path+File.separator+fileName;	
		return new FileInputStream(filePath);
	}//method
	
	

}
