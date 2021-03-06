package com.turvo.bankingqueue.entity;
// Generated 21 Mar, 2019 4:14:11 PM by Hibernate Tools 5.0.6.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "employee", catalog = "abc_bank")
public class Employee implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int employeeId;
	private String name;
	private Set<Counter> counters = new HashSet<Counter>(0);
	private Set<EmployeeRole> employeeRoles = new HashSet<EmployeeRole>(0);

	public Employee() {
	}

	public Employee(int employeeId, String name) {
		this.employeeId = employeeId;
		this.name = name;
	}

	public Employee(int employeeId, String name, Set<Counter> counters, Set<EmployeeRole> employeeRoles) {
		this.employeeId = employeeId;
		this.name = name;
		this.counters = counters;
		this.employeeRoles = employeeRoles;
	}

	@Id

	@Column(name = "employee_id", unique = true, nullable = false)
	public int getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<Counter> getCounters() {
		return this.counters;
	}

	public void setCounters(Set<Counter> counters) {
		this.counters = counters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<EmployeeRole> getEmployeeRoles() {
		return this.employeeRoles;
	}

	public void setEmployeeRoles(Set<EmployeeRole> employeeRoles) {
		this.employeeRoles = employeeRoles;
	}

}
