package com.example.main.services;

import java.util.Map;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServiceImplementation implements CloudinaryService {
	
	private final Cloudinary cloudinary;

    public CloudinaryServiceImplementation(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

	public boolean deleteFromCloudinary(String publicId, String resourceType) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap(
                "resource_type", resourceType
            ));
            return "ok".equals(result.get("result"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
