package com.refresh.serialization;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class DeserializeDemo implements Serializable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Student s = null;
		try{
			FileInputStream f = new FileInputStream("/tmp/student.ser");
			ObjectInputStream in = new ObjectInputStream(f);
			s = (Student) in.readObject();
			in.close();
			f.close();
			
			System.out.println("Deserialized Student....");
			System.out.println("Name : " + s.name);
			System.out.println("Department : " + s.department);
			System.out.println("College Name : " + s.collegeName);
			System.out.println("Roll ID :" + s.rollId);
			
			s.displayName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
