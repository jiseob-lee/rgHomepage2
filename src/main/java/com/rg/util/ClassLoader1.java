package com.rg.util;

public class ClassLoader1 {

	public static void main(String[] args) {
        ClassLoader loader = ClassLoader1.class.getClassLoader();
        
        String classLocation = ClassLoader1.class.getName().replace('.', '/') + ".class";
        
        System.out.println(classLocation);
        System.out.println(loader.getResource("org/springframework/scheduling/quartz/JobDetailFactoryBean.class"));
        System.out.println(loader.getResource("com/rg/util/GeoLite2.class"));
        System.out.println(loader.getResource("org/springframework/aop/Advisor.class"));
        
        System.out.println(loader.getResource("javax/servlet/RequestDispatcher.class"));
        
        
        System.out.println(loader.getResource(classLocation));
        
	}

}
