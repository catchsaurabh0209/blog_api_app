package com.blogApiApp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogApiApp.entities.Category;
import com.blogApiApp.entities.Post;
import com.blogApiApp.entities.User;
import com.blogApiApp.exceptions.ResourceNotFoundException;
import com.blogApiApp.payloads.PostDto;
import com.blogApiApp.payloads.PostResponse;
import com.blogApiApp.repositories.CategoryRepo;
import com.blogApiApp.repositories.PostRepo;
import com.blogApiApp.repositories.UserRepo;
import com.blogApiApp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException(" User ", " Id ", userId));
		
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException(" category ", " Id ", categoryId));
		
		Post post= modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
		
		return (modelMapper.map(createdPost, PostDto.class));
	}

	@Override
	public PostDto upadatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub

		Post post=postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		Post updatedPost= this.postRepo.save(post);
		
		return (modelMapper.map(updatedPost, PostDto.class));
	}
	public PostDto upadatePostImage(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub

		Post post=postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setImageName(postDto.getImageName());
		Post updatedPost= this.postRepo.save(post);
		
		return (modelMapper.map(updatedPost, PostDto.class));
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		
		Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		this.postRepo.delete(post);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
		
		
		return (modelMapper.map(post, PostDto.class));
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		// TODO Auto-generated method stub
		
		Pageable p;
		if(sortOrder.equalsIgnoreCase("asc"))
			p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
		else
			p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		
		Page<Post> pagePosts= this.postRepo.findAll(p);
		
		List<Post> posts=pagePosts.getContent();
		
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElement(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "CategroyId", categoryId));
		
		List<Post> posts= this.postRepo.findByCategory(category);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		List<Post> posts= this.postRepo.findByUser(user);
		List<PostDto> postDtos= posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
