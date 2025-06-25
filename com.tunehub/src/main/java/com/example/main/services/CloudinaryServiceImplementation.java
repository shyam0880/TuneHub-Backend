package com.example.main.services;

import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServiceImplementation implements CloudinaryService {
	
	private final Cloudinary cloudinary;

    public CloudinaryServiceImplementation(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map uploadToCloudinary(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

    @Override
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
