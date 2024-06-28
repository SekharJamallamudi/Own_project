package com.Project.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
         
	private Long id;
	private String firstName;
	private String email;
	private String password;
	private MultipartFile profileImage;

}
