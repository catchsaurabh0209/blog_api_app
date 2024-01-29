package com.blogApiApp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApiApp.config.AppConstants;
import com.blogApiApp.payloads.ApiResponse;
import com.blogApiApp.payloads.PostDto;
import com.blogApiApp.payloads.PostResponse;
import com.blogApiApp.services.FileService;
import com.blogApiApp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// POST
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto ,@PathVariable Integer userId, @PathVariable Integer categoryId)
	{
		PostDto createdPost= postService.createPost(postDto, userId, categoryId); 
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	// UPDATE
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
	{
		PostDto updatedPost=this.postService.upadatePost(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}
	
	// DELETE
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse> (new ApiResponse("User delete successfully", true), HttpStatus.OK);
	}
	
	// GET post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		return (ResponseEntity.ok(postService.getPostById(postId)));
	}
	
	// GET all the post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam (value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE, required=false ) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortOrder", defaultValue=AppConstants.SORT_ORDER, required=false) String sortOrder)
	{
		
		return (ResponseEntity.ok(this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder)));
	}
	
	// GET the post by particular USER
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> postDtos=this.postService.getPostsByUser(userId);
		
		return ResponseEntity.ok(postDtos);
	}
	
	// GET the post by particular Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);

		return ResponseEntity.ok(postDtos);
	}
	
	
	// SEARCHING the post by title
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword)
	{
		return ResponseEntity.ok(this.postService.searchPost(keyword));
	}
	
	@PostMapping("posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException
	{
		PostDto postDto= this.postService.getPostById(postId);
		String fileName= this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPost= this.postService.upadatePostImage(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}
	
	// TO GET Image
	@GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
}
