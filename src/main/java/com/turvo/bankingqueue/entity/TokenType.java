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
 * TokenType generated by hbm2java
 */
@Entity
@Table(name = "token_type", catalog = "abc_bank")
public class TokenType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tokenTypeId;
	private String name;
	private Set<Token> tokens = new HashSet<Token>(0);
	private Set<TokenSubTask> tokenSubTasks = new HashSet<TokenSubTask>(0);

	public TokenType() {
	}

	public TokenType(String name, Set<Token> tokens, Set<TokenSubTask> tokenSubTasks) {
		this.name = name;
		this.tokens = tokens;
		this.tokenSubTasks = tokenSubTasks;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "token_type_id", unique = true, nullable = false)
	public Integer getTokenTypeId() {
		return this.tokenTypeId;
	}

	public void setTokenTypeId(Integer tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tokenType")
	public Set<Token> getTokens() {
		return this.tokens;
	}

	public void setTokens(Set<Token> tokens) {
		this.tokens = tokens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tokenType")
	public Set<TokenSubTask> getTokenSubTasks() {
		return this.tokenSubTasks;
	}

	public void setTokenSubTasks(Set<TokenSubTask> tokenSubTasks) {
		this.tokenSubTasks = tokenSubTasks;
	}

}
