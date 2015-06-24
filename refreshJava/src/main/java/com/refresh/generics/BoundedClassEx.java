package com.refresh.generics;

public class BoundedClassEx <T extends BoundedA>{

	private T obj = null;
	
	public BoundedClassEx(T params){
		this.obj = params;
	}
	
	public void checkClass(){
		this.obj.printClass();
	}
}
