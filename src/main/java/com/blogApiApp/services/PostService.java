package com.blogApiApp.services;

import java.util.List;


import com.blogApiApp.payloads.PostDto;
import com.blogApiApp.payloads.PostResponse;

public interface PostService{

	// create 
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// Update
	PostDto upadatePost(PostDto postDto, Integer postId);
	
	// update image
	PostDto upadatePostImage(PostDto postDto, Integer postId);
	
	// Delete 
	void deletePost(Integer postId);
	
	// Get Single post
	PostDto getPostById(Integer postId);
	
	// Get all the post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortOrder);
	
	
	// Get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// Get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	// Search Post
	List<PostDto> searchPost(String keyword);
	
	
}
