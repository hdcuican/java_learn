package com.java.core.annotation;

/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年7月30日
 */
@Table(name="table_user")
public class User {
	
	@Column(name="name", type="varchar2")
	private String name;
	
	@Column(name="age", type="int")
	private int age;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
	
	

}
