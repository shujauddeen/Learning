package com.refresh.generics;

public class GenericFactory<E> {

	private Class theClass = null;
	
	public GenericFactory(Class theClass){
		this.theClass = theClass;
	}
	
	@SuppressWarnings("unchecked")
	public E getInstance() throws InstantiationException, IllegalAccessException{
		return (E) this.theClass.newInstance();
	}
}
