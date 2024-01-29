package com.blogApiApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApiApp.entities.Category;
import com.blogApiApp.entities.Post;
import com.blogApiApp.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	
	// custom finder method 
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category cat);
	List<Post> findByTitleContaining(String title);
	
}
