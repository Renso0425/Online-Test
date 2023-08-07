package onlineTest;

import java.io.*;

public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	public Student(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
