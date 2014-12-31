package com.refresh.serialization;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SerializeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Student s = new Student();
		s.name = "Shuja";
		s.department = "Information Technology";
		s.collegeName = "Anna University";
		s.rollId = 1234;
		
		try{
			FileOutputStream f = new FileOutputStream("/tmp/student.ser");
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(s);
			out.close();
			f.close();
			System.out.println("Serialized data is saved in /tmp/student.ser");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
