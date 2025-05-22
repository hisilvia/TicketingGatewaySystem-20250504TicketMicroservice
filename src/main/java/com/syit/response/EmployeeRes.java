package com.syit.response;

import java.util.Collection;
import java.util.Set;

public class EmployeeRes {

	private long id;
	private String name;
	private String email;
	private String password;
	private String department;
	private String project;
	private long managerId;
       
	private Collection<RoleRes> roles;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
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

	public Collection<RoleRes> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleRes> role) {
		this.roles = role;
	}

	

	
	
	
	
	
}
