package com.refresh.generics;

public class GenericMethods {

	public static void main(String[] args) {
		
		/*
		 * Print array of any type
		 * */
		Integer[] intArray = {1,2,3,4};
		String[] strArray = {"a", "b", "c"};
		Double[] dlbArray = {1.2, 2.3, 5.6};
		
		System.out.println("Array integer, contains");
		printArray(intArray);
		
		System.out.println("Array string, contains");
		printArray(strArray);
		
		System.out.println("Array double, contains");
		printArray(dlbArray);
		
		/*
		 * Find the max in the given obj
		 */
		
		System.out.printf( "Max of %d, %d and %d is %d\n\n", 3, 4, 5, maximum( 3, 4, 5 ) );

		System.out.printf( "Maxm of %.1f,%.1f and %.1f is %.1f\n\n", 6.6, 8.8, 7.7, maximum( 6.6, 8.8, 7.7 ) );

		System.out.printf( "Max of %s, %s and %s is %s\n","pear", "apple", "orange", maximum( "pear", "apple", "orange" ) );
		
	}

	public static <T extends Comparable<T>> T maximum(T x, T y, T z){
		
		T max = x;
		
		if(y.compareTo(x) > 0)
			max = y;
		if(z.compareTo(z) > 0)
			max = z;
		
		return max;
	}
	
	//print the array
	public static <T> void printArray(T[] array){
		for(T element : array){
			System.out.printf("%s ", element);
		}
		System.out.println();
	}
	
	
}
