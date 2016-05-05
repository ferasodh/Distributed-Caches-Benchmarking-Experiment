package com.performance.model;

import java.io.Serializable;
/**
 *
 * @author Haytham Salhi and Rabee Naser
 *
 *
 */
public class Employee implements Serializable {
	private static final long serialVersionUID = 553885034467846744L;

	private int ID;
	private String name;
	private int age;
	private String password;
	private Organization organization;



	public Employee() {}

	public Employee(String name, int age,String password, Organization organization) {
		super();
		this.name = name;
		this.age = age;
		this.password = password;
		this.organization = organization;
	}

	/**
	 * @return the ID
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param name the name to set
	 */
	public void setID(int ID) {
		this.ID = ID;
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
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + " password=" + password + " orgnization name "+ organization.getName() +"]";
	}
}