package com.example.main.services;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;		//security feature only enable when needed
//import org.springframework.security.crypto.password.PasswordEncoder;			//security feature only enable when needed
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.UserDTO;
import com.example.main.dto.UserRegisterDTO;
import com.example.main.entity.Users;
import com.example.main.repository.UsersRepository;

@Service
public class UsersServiceImplementation implements UsersService{
	
	@Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Cloudinary cloudinary;
    
    @Autowired
    private PlaylistService playlistService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
    	if (usersRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            return "Username already taken";
        }

        if (usersRepository.findByEmail(userRegisterDTO.getEmail()) != null) {
            return "Email already registered";
        }
        Users user = convertToEntity(userRegisterDTO);
        usersRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public UserDTO signin(String username, String password) {
        Optional<Users> optionalUser = usersRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return null;
        }
        Users user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) return null;
        return convertToDTO(optionalUser.get());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Optional<Users> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) return null;
        return convertToDTO(user.get());
    }

    @Override
    public Map<Boolean, String> updateUser(Long id, UserDTO userDTO) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        Map<Boolean, String> message = new HashMap<>();
        if (optionalUser.isEmpty()) {
            message.put(false, "User not found");
            return message;
        }
        Users user = optionalUser.get();
        String username = userDTO.getUsername();
        if (username != null) {
            if (usersRepository.findByUsername(username) != null) {
                message.put(false, "Username already exists");
                return message;
            } else {
                user.setUsername(username);
            }
        }
        if (userDTO.getAddress() != null) user.setAddress(userDTO.getAddress());
        if (userDTO.getGender() != null) user.setGender(userDTO.getGender());

        usersRepository.save(user);
        message.put(true, "User updated successfully");
        return message;
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) return false;
        Users user = optionalUser.get();
        
        if(!playlistService.deleteUserWithPlaylists(user.getId())) return false;

        if (user.getImageId() != null) {
            deleteImage(user.getImageId());
        }
        usersRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUserPhoto(Long id, MultipartFile image) {
    	Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) return false;
        Users user = optionalUser.get();

        if (user.getImageId() != null) {
            deleteImage(user.getImageId());
        }
        Map<String, Object> uploadResult = uploadImage(image);
        user.setImage((String) uploadResult.get("secure_url"));
        user.setImageId((String) uploadResult.get("public_id"));
        usersRepository.save(user);
        return true;
    }

    @Override
    public boolean removeProfileImage(Long id) {
    	Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) return false;
        Users user = optionalUser.get();

        if (user.getImageId() != null) {
            deleteImage(user.getImageId());
            user.setImage(null);
            user.setImageId(null);
            usersRepository.save(user);
        }
        return true;
    }

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
    public boolean updatePrimeStatus(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) return false; 
        user.setPremium(true);
        usersRepository.save(user);
        return true;
    }

	@Override
	public UserDTO findByEmail(String email) {
		return convertToDTO(usersRepository.findByEmail(email));
	}

    private Users convertToEntity(UserRegisterDTO dto) {
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setRole("USER");
        user.setPremium(false);
        return user;
    }

    private UserDTO convertToDTO(Users user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getRole(),
                user.getAddress(),
                user.getImage(),
                user.isPremium()
        );
    }	    
}
