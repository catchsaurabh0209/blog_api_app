package com.blogApiApp.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="catagory")
@Setter
@Getter
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	
	@Column(name="title")
	private String categoryName;
	
	@Column(name="description")
	private String categoryDesc;
	
	
	@OneToMany(mappedBy ="category", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	private List<Post> posts= new ArrayList<>();
}
