package com.example.main.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
	
	public Map uploadToCloudinary(MultipartFile file) throws IOException;
	
	public boolean deleteFromCloudinary(String publicId, String resourceType);
	
}
