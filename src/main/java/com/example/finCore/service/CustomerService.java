package com.example.finCore.service;

import com.example.finCore.DTO.CustomerRequestDTO;
import com.example.finCore.entity.Customer;
import com.example.finCore.exception.DataAlreadyExist;
import com.example.finCore.exception.ImmutableFieldException;
import com.example.finCore.exception.NotFoundException;
import com.example.finCore.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer add(CustomerRequestDTO customer) {

        customerRepo.findByEmail(customer.getEmail()).ifPresent(c->{
            throw new DataAlreadyExist("Email already present");
        });

        customerRepo.findByPanNumber(customer.getPanNumber()).ifPresent(c->{
            throw new DataAlreadyExist("Pan Number Already exists");
        });

        Customer newCustomer=new Customer();

        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setPhone(customer.getPhoneNumber());
        newCustomer.setPanNumber(customer.getPanNumber());

        return customerRepo.save(newCustomer);

    }

    public Customer getCustomer(long id) {
        Customer user = null;
        user = customerRepo.findById(id).orElseThrow(() -> new NotFoundException("user not found"));
        return user;
    }

    @Transactional
    public Customer updateUser(Long id, CustomerRequestDTO customerDTO) {

        Customer user = customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        if (!user.getEmail().equalsIgnoreCase(customerDTO.getEmail())) {
            throw new ImmutableFieldException("Email cannot be modified once created");
        }

        user.setAddress(customerDTO.getAddress());
        user.setPanNumber(customerDTO.getPanNumber());


        return user;
    }

    public void deleteUser(Long id) {
        Customer user = customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        customerRepo.delete(user);
    }
}
