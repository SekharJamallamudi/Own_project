package com.Project.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String email;
	private String password;
	private String profileImage;
	
	 @OneToMany(targetEntity = Role.class,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	    @JoinColumn(name ="user_id",referencedColumnName = "id")
	    private List<Role>roles ; 

}
