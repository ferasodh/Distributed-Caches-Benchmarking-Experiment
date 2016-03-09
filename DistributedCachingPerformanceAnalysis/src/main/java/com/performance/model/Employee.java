package com.performance.model;

import java.io.Serializable;
/**
 * 
 * @author Haytham Salhi
 *
 */
public class Employee implements Serializable {
	private static final long serialVersionUID = 553885034467846744L;
	
	private String name;
	private String age;
	
	public Employee() {}
	
	public Employee(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + "]";
	}
}
