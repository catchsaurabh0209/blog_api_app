package com.blogApiApp.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private int categoryId;
	
	@NotNull
	@Size(min=3, max = 8)
	private String categoryName;
	
	@NotNull
	@Size(min=10, max=100)
	private String categoryDesc;
}
