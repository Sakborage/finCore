package com.example.finCore.controller;

import com.example.finCore.DTO.CustomerRequestDTO;
import com.example.finCore.entity.Customer;
import com.example.finCore.service.CustomerService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String getCustomers(){
        return "hello world";
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerRequestDTO customer){

        return new ResponseEntity<>(customerService.add(customer),HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id){
            Customer customer = customerService.getCustomer(id);
            return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid  @RequestBody CustomerRequestDTO customer){

        Customer customer1 = customerService.updateUser(id, customer);
        return new ResponseEntity<>(customer1,HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
          customerService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
