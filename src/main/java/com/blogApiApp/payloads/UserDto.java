package com.blogApiApp.payloads;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	
	private int id;
	
	@NotEmpty
	@Size(min=3, message="Name size should be greater than 3 and less than 15")
	private String name;
	
	@Email(message="Please enter the valid email")
	private String email;
	
	@NotEmpty
	@Size(min=3, message="password size should have atleast 3 character")
	private String password;
	
	@NotEmpty(message="Please enter someting in about section")
	private String about;
}
