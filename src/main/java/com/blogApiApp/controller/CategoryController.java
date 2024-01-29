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
import com.blogApiApp.payloads.CategoryDto;
import com.blogApiApp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	// POST
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		return (new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED));
	}
	
	// UPDATE
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
	{
		return (ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId)));
	}
	
	// GET
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId)
	{
		return (ResponseEntity.ok(this.categoryService.getSingleCategory(categoryId)));
	}
	
	//GET ALL
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategory(), HttpStatus.OK);
	}
	
	// DELETE
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId)
	{
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse> (new ApiResponse("Category delete successfully", true), HttpStatus.OK);
	}
}
