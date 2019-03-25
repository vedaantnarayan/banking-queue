package com.turvo.bankingqueue.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.EmployeeRole;

@Repository
public interface EmployeeRoleRepository extends CrudRepository<EmployeeRole, Integer> {

	@Query("select er.role.name from EmployeeRole er where er.employee.employeeId = ?1")
	 String findEmployeeRoleById(Integer employeeId);
}
