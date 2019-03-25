package com.turvo.bankingqueue.entity;
// Generated 21 Mar, 2019 3:04:51 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CounterService generated by hbm2java
 */
@Entity
@Table(name = "counter_service", catalog = "abc_bank")
public class CounterService implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer counterServiceId;
	private Counter counter;
	private Service service;

	public CounterService() {
	}

	public CounterService(Counter counter, Service service) {
		this.counter = counter;
		this.service = service;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "counter_service_id", unique = true, nullable = false)
	public Integer getCounterServiceId() {
		return this.counterServiceId;
	}

	public void setCounterServiceId(Integer counterServiceId) {
		this.counterServiceId = counterServiceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "counter_id", nullable = false)
	public Counter getCounter() {
		return this.counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false)
	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
