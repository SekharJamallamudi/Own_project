package com.Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

	List<Address> findByUserId(Long userId);

}
