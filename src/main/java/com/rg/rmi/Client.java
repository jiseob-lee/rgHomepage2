package com.rg.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? "13.209.88.125" : args[0];
        
        System.setProperty("java.security.policy","file:/H:/eclipse-workspace/rg-aws/src/com/rg/rmi/client.policy");
        
        //if (System.getSecurityManager() == null) {
            //System.setSecurityManager(new SecurityManager());
        //}
        
        try {
            Registry registry = LocateRegistry.getRegistry(host, 2000);
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
