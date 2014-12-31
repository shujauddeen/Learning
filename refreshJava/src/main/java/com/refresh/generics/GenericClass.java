package com.refresh.generics;

public class GenericClass<T> {

	T obj;
	
	public void set(T t){
		this.obj = t;
	}
	
	public T get(){
		return obj;
	}
	
	public static void main(String[] args) {
		
		GenericClass<Integer> intObj = new GenericClass<Integer>();
		intObj.set(new Integer(10));
		
		GenericClass<String> strObj = new GenericClass<String>();
		strObj.set(new String("Hello!"));
		
		System.out.printf("Integer value: %d\n\n",intObj.get());
		System.out.printf("String value: %s\n\n",strObj.get());
	}
}
