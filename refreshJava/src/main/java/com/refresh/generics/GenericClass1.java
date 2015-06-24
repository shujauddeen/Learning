package com.refresh.generics;

public class GenericClass1<T> {
	
	private T refObj = null;
	
	public GenericClass1(T params){
		this.refObj = params;
	}
	
	public T getRefObj(){
		return this.refObj;
	}
	
	public void printType(){
		System.out.println("Type : " + refObj.getClass().getName());
	}
}
