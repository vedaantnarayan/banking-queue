package com.turvo.bankingqueue.entity;
// Generated 21 Mar, 2019 3:04:51 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Service generated by hbm2java
 */
@Entity
@Table(name = "service", catalog = "abc_bank")
public class Service implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer serviceId;
	private String name;
	private Set<CounterService> counterServices = new HashSet<CounterService>(0);
	private Set<TokenSubTask> tokenSubTasks = new HashSet<TokenSubTask>(0);

	public Service() {
	}

	public Service(String name) {
		this.name = name;
	}

	public Service(String name, Set<CounterService> counterServices, Set<TokenSubTask> tokenSubTasks) {
		this.name = name;
		this.counterServices = counterServices;
		this.tokenSubTasks = tokenSubTasks;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "service_id", unique = true, nullable = false)
	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
	public Set<CounterService> getCounterServices() {
		return this.counterServices;
	}

	public void setCounterServices(Set<CounterService> counterServices) {
		this.counterServices = counterServices;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
	public Set<TokenSubTask> getTokenSubTasks() {
		return this.tokenSubTasks;
	}

	public void setTokenSubTasks(Set<TokenSubTask> tokenSubTasks) {
		this.tokenSubTasks = tokenSubTasks;
	}

}
