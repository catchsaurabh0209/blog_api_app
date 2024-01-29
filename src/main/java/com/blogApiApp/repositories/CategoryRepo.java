package com.blogApiApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApiApp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
