package com.refresh.generics;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TestGenerics {


	public static void main(String[] args) throws UnsupportedEncodingException {
		
		//-----------------------------PART1-------------------
		GenericClass1 <String> sobj = new GenericClass1<String>(new String("hello world"));
		sobj.printType();
		GenericClass1<Boolean> bObj =  new GenericClass1<Boolean>(new Boolean(true));
		bObj.printType();
		System.out.println(bObj.getRefObj().booleanValue());
		System.out.println(new String(sobj.getRefObj().getBytes(), "UTF-8"));

		//---------------------------------PART 2------------------
		BoundedClassEx<BoundedA> objA = new BoundedClassEx<BoundedA>(new BoundedA());
		objA.checkClass();
		
		BoundedClassEx<BoundedB> objB =  new BoundedClassEx<BoundedB>(new BoundedB());
		objB.checkClass();
		
		//--------------------------------PART 3-------------------
		BoundedClassIn<BoundedX> objX = new BoundedClassIn<BoundedX>(new BoundedX());
		objX.doTestRun();
		
		BoundedClassIn<BoundedY> objY = new BoundedClassIn<BoundedY>(new BoundedY());
		objY.doTestRun();
		
		//--------------------------------PART 4-------------------
		try {
			GenericFactory<BoundedA> gf = new GenericFactory<BoundedA>(BoundedA.class);
			BoundedA ba = gf.getInstance();
			ba.printClass();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		
	}
}
