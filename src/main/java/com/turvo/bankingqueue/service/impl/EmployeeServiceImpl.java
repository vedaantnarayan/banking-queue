/**
 * 
 */
package com.turvo.bankingqueue.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turvo.bankingqueue.constant.EmployeeRole;
import com.turvo.bankingqueue.repository.EmployeeRoleRepository;
import com.turvo.bankingqueue.service.EmployeeService;

/**
 * @author vedantn
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRoleRepository employeeRoleRepository;
	
	@Override
	public EmployeeRole getEmployeeRole(Integer employeeId) {
		String empRole = employeeRoleRepository.findEmployeeRoleById(employeeId);
		return EmployeeRole.valueOf(empRole);
	}

}
