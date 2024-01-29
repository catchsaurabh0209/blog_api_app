package com.blogApiApp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApiApp.entities.User;
import com.blogApiApp.exceptions.ResourceNotFoundException;
import com.blogApiApp.payloads.UserDto;
import com.blogApiApp.repositories.UserRepo;
import com.blogApiApp.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		User user= this.DtoToUser(userDto);
		
		User savedUser= this.userRepo.save(user);
		
		return(UserToDto(savedUser));
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException(" User ", " Id ", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(user.getPassword());
		user.setAbout(userDto.getAbout());
		
		
		User updatedUser=this.userRepo.save(user);
		
		
		return (this.UserToDto(updatedUser));
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return (this.UserToDto(user));
	}

	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users = userRepo.findAll();
		List<UserDto> userDto=users.stream().map(user-> this.UserToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException(" User ", " Id ", userId));
		
		this.userRepo.delete(user);

	}
	
	private User DtoToUser(UserDto userDto)
	{
		User user= modelMapper.map(userDto, User.class);
		
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		return user;
		
	}
	
	private UserDto UserToDto(User user)
	{
		UserDto userDto= modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
		
	}
}
