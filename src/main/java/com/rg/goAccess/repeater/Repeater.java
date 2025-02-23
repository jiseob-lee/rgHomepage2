package com.rg.goAccess.repeater;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Repeater {
	
	private static int no = 0;

	public static void resetNo() {
		no = 0;
	}
	
    public static int repeat() throws Exception {
    	
    	no++;
        
    	ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Task());

        try {
            
        	System.out.println("Started..");
            System.out.println(future.get(3, TimeUnit.SECONDS));
            System.out.println("Finished!");
            
            File folder = new File("/home/ubuntu/apache-tomcat-10.1.36/webapps/ROOT/goaccess");
    		File[] files = folder.listFiles();
    		if (files != null &&  files.length > 0) {
    			return 1;
    		} else {
    			if (no > 50) {
    				return 0;
    			}
    			Repeater.repeat();
    		}
    		
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        }

        executor.shutdownNow();
        
        return 1;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Task());

        try {
            System.out.println("Started..");
            System.out.println(future.get(3, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        }

        executor.shutdownNow();
    }
}

class Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(500); // Just to demo a long running task of 4 seconds.
        return "Ready!";
    }
}