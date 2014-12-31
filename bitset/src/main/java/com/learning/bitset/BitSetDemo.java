package com.learning.bitset;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetDemo {
	
	public static void main(String args[]) {
		
		BitSetDemo obj = new BitSetDemo();
		obj.simpleBitSet();
	}
	
	public void simpleBitSet(){
		
		BitSet bits1 = new BitSet(16);
		BitSet bits2 = new BitSet(16);
		
		//set some bits
		for(int i=0; i<16; i++){
			if((i%2) == 0)
				bits1.set(i);
			if((i%5) != 0)
				bits2.set(i);
		}
		
		List<BitSet> myData = new ArrayList<BitSet>();
		
		myData.add(bits1);
		myData.add(bits2);
		
		System.out.println("MyData : " + myData.toString());
		
		System.out.println("Initial pattern in bits1: ");
		System.out.println(bits1);
		System.out.println("\nInitial pattern in bits2: ");
		System.out.println(bits2);
		
		// XOR bits
		bits2.xor(bits1);
		System.out.println("\nbits2 XOR bits1: ");
		System.out.println(bits2);
		
		// OR bits
		bits2.or(bits1);
		System.out.println("\nbits2 OR bits1: ");
		System.out.println(bits2);
		
		// AND bits
		bits2.and(bits1);
		System.out.println("\nbits2 AND bits1: ");
		System.out.println(bits2);
		
		String text = "Hello World!";
		String binary = new BigInteger(text.getBytes()).toString(2);
		intToString(binary);
		stringToInt(text);
		
	}
	
	public void intToString(String binary){
		String text2 = new String(new BigInteger(binary, 2).toByteArray());
		System.out.println("As text: "+text2);
	}
	
	public void stringToInt(String text){
		String binary = new BigInteger(text.getBytes()).toString(2);
		System.out.println("As binary: "+binary);
	}
	
}
