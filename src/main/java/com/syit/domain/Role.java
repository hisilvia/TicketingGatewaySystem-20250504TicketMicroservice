package com.syit.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


public class Role {

	
	//private long roleId;
	
	private String roleName;
	
	//@ManyToMany(mappedBy="roles")
	//Set<Employee> employee = new HashSet<>();

	/*
	public Role() {
		super();
	}

	public Role(long roleId, String roleName, Set<Employee> employee) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.employee = employee;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
    */
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/*
	public Set<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
	}
    */
	
	
	
}
