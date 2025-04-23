package com.example.main.services;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.UserDTO;
import com.example.main.entity.Users;
import com.example.main.repository.UsersRepositories;

@Service
public class UsersServiceImplementation implements UsersService{
	@Autowired
	UsersRepositories usersRepositories;
	
	@Autowired
	private Cloudinary cloudinary;

	@SuppressWarnings("unchecked")
	private Map<String, Object> uploadImage(MultipartFile image) {
	    try {
	        return cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to upload image", e);
	    }
	}

	private void deleteImage(String publicId) {
	    try {
	        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to delete image", e);
	    }
	}
	
	 @Override
	    public void removeProfileImage(Long userId) {
	        Users user = usersRepositories.findById(userId);

	        if (user.getImageId() != null) {
	            deleteImage(user.getImageId());
	            user.setImageId(null);
	            user.setImage(null);
	            usersRepositories.save(user);
	        }
	    }
	
	@Override
	public String addUser(Users user) {
		usersRepositories.save(user);
		return "User added Successfully, Try Login";
	}
	
	@Override
	public void updateUserPhoto(int id, MultipartFile image) {
	    Users user = usersRepositories.findById(id);

	    // Delete existing image if any
	    if (user.getImageId() != null && !user.getImageId().isEmpty()) {
	        deleteImage(user.getImageId());
	    }

	    // Upload new image
	    Map<String, Object> uploadedData = uploadImage(image);
	    String imageUrl = (String) uploadedData.get("secure_url");
	    String imageId = (String) uploadedData.get("public_id");

	    user.setImage(imageUrl);
	    user.setImageId(imageId);
	    usersRepositories.save(user);
	}
	

	@Override
	public boolean emailExists(String email) {
		if(usersRepositories.findByEmail(email)==null)
		{
			return false;
		}
		else
		{
			return true;			
		}
	}

	@Override
	public boolean validateUser(String email, String password) {
		Users user = usersRepositories.findByEmail(email);
		String db_password= user.getPassword();
		if(password.equals(db_password))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getRole(String email) {
		Users user = usersRepositories.findByEmail(email);
		return user.getRole();
	}

	@Override
	public UserDTO getUser(String email) {
		Users user = usersRepositories.findByEmail(email);
		return convertToDTO(user);
	}

	@Override
	public void updateUser(Users user) {
		usersRepositories.save(user);
		
	}
	
	@Override
	public boolean updatePrimeStatus(String email) {
        if (emailExists(email)) {
        	Users user = usersRepositories.findByEmail(email);
            user.setPremium(true);  
            usersRepositories.save(user);
            return true;
        }
        return false;
    }

	@Override
	public Users findById(long id) {
		return usersRepositories.findById(id);
	}
	
	public void deleteUser(int id) {
	    Users user = usersRepositories.findById(id);
	    if (user.getImageId() != null && !user.getImageId().isEmpty()) {
	        deleteImage(user.getImageId());
	    }
	    usersRepositories.deleteById(id);
	}

	@Override
	public List<Users> getAllUsers() {
		return usersRepositories.findAll();
	}
	
	private UserDTO convertToDTO(Users user) {
		UserDTO songdto = new UserDTO(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getGender(),
				user.getRole(),
				user.getAddress(),
				user.getImage(),
				user.isPremium()
				);
		return songdto;
		
	}

	@Override
	public Users findByEmail(String email) {
		return usersRepositories.findByEmail(email);	
	}

	
	

}
