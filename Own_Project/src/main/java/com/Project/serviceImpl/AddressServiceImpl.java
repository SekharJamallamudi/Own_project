package com.Project.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.entity.Address;
import com.Project.repository.AddressRepository;
import com.Project.service.AddressService;
@Service
public class AddressServiceImpl implements AddressService {

	 @Autowired
	    private AddressRepository addressRepository;
	    
	    public Address addAddress(Address address) {
	        return addressRepository.save(address);
	    }
	    
	    public List<Address> getUserAddresses(Long userId) {
	        return addressRepository.findByUserId(userId);
	    }

}
