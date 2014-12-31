package com.refresh.serialization;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Student implements Serializable{

	public String name;
	public String department;
	public transient String collegeName;
	public int rollId;
	
	public void displayName(){
		System.out.println("Student name : " + name);
	}
}
