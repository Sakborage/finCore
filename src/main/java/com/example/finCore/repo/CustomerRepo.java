package com.example.finCore.repo;

import com.example.finCore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    <T> Optional<T> findByEmail(String email);

    <T> Optional<T> findByPanNumber(String panNumber);


}
