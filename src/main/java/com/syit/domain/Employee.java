package com.syit.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

public class Employee {

	
	//private long id;
	private String name;
	private String email;
	private String password;
	private String department;
	private String project;
	private long managerId;
	
	/*
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="employees_roles", 
			joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"), 
	        inverseJoinColumns = @JoinColumn(name = "role_id"))
	*/        
	private Collection<Role> roles;
	
	/*
	//private Set<Role> roles = new HashSet<>();
	public Employee() {
		super();	
	}

	public Employee(long id, String name, String email, String password, String department, String project,
			long managerId, Collection<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.department = department;
		this.project = project;
		this.managerId = managerId;
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public long getManagerId() {
		return managerId;
	}

	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> role) {
		this.roles = role;
	}

	

	
	
	
	
	
}
