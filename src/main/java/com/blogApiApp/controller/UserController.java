package com.blogApiApp.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApiApp.payloads.ApiResponse;
import com.blogApiApp.payloads.UserDto;
import com.blogApiApp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//	 POST --> create user in database;
	@PostMapping("/")
	public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto)
	{
		UserDto createdUser=this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	// PUT --> to update the user
	// this {userid} is called path uri variable.
	@PutMapping("/{userId}")  
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uId )
	{
		UserDto updatedUser = userService.updateUser(userDto, uId);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	// DELETE --> to delete the user
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> delete(@PathVariable Integer userId)
	{
		userService.deleteUser(userId);
		
		// return new ResponseEntity<> (Map.of("message", "user deleted successfully"), HttpStatus.OK);
		// Instead of using map we will create and Api response class, and will pass its object here.
		
		
		return new ResponseEntity<ApiResponse> (new ApiResponse("User delete successfully", true), HttpStatus.OK);
	}
	
	
	// GET --> to get all the user details
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		return new ResponseEntity<List<UserDto>>(userService.getAllUser(), HttpStatus.OK);
	}
	
	// GET --> to get single user details
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return new ResponseEntity<UserDto>(userService.getUserById(userId), HttpStatus.OK);
	}
	
}
