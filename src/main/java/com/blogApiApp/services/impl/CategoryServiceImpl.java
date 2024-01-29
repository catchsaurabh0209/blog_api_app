package com.blogApiApp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApiApp.entities.Category;
import com.blogApiApp.exceptions.ResourceNotFoundException;
import com.blogApiApp.payloads.CategoryDto;
import com.blogApiApp.repositories.CategoryRepo;
import com.blogApiApp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category= this.modelMapper.map(categoryDto, Category.class);
		
		Category createdUser= this.categoryRepo.save(category);		
		return (modelMapper.map(createdUser, CategoryDto.class));
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException(" category ", " Id ", categoryId));
		
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDesc(categoryDto.getCategoryDesc());
		
		Category updatedCategory= this.categoryRepo.save(category);
		return (modelMapper.map(updatedCategory, CategoryDto.class));
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException(" category ", " Id ", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException(" category ", " Id ", categoryId));
		

		return (modelMapper.map(category, CategoryDto.class));
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> category=this.categoryRepo.findAll();
		List<CategoryDto> categoryDto=category.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDto;
	}

}
