package com.shuja.redis;

public class StartTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RedisClient th1 = new RedisClient("thread1");
		RedisClient th2 = new RedisClient("thread2");
		RedisClient th3 = new RedisClient("thread3");
		RedisClient th4 = new RedisClient("thread4");
		RedisClient th5 = new RedisClient("thread5");
		
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
		
		boolean thread1IsAlive = true;
        boolean thread2IsAlive = true;
        boolean thread3IsAlive = true;
        boolean thread4IsAlive = true;
        boolean thread5IsAlive = true;
        
        
        
        do {
            if (thread1IsAlive && !th1.isAlive()) {
                thread1IsAlive = false;
                System.out.println("Thread 1 is dead.");
            }
            if (thread2IsAlive && !th2.isAlive()) {
                thread2IsAlive = false;
                System.out.println("Thread 2 is dead.");
            }
            if (thread3IsAlive && !th3.isAlive()) {
                thread3IsAlive = false;
                System.out.println("Thread 3 is dead.");
            }
            if (thread4IsAlive && !th4.isAlive()) {
                thread4IsAlive = false;
                System.out.println("Thread 4 is dead.");
            }
            if (thread5IsAlive && !th5.isAlive()) {
                thread5IsAlive = false;
                System.out.println("Thread 5 is dead.");
            }
            
        } while(thread1IsAlive ||  thread2IsAlive || thread3IsAlive || thread4IsAlive || thread5IsAlive);
	}

}
