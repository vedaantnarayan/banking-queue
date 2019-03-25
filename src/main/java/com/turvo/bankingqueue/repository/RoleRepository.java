package com.turvo.bankingqueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
