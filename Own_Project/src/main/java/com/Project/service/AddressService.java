package com.Project.service;

import java.util.List;

import com.Project.entity.Address;

public interface AddressService {

	Address addAddress(Address address);

	List<Address> getUserAddresses(Long userId);

}
