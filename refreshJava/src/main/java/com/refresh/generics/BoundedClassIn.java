package com.refresh.generics;

public class BoundedClassIn<T extends BoundedInterface> {

	private T obj;
	
	public BoundedClassIn(T params){
		this.obj = params;
	}
	
	public void doTestRun(){
		this.obj.printClass();
	}
}
