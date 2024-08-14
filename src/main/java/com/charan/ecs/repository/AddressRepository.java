package com.charan.ecs.repository;

import com.charan.ecs.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    public void deleteByCustomerId(int customerId);

    public List<Address> findAllByCustomerId(int customerId);
}
