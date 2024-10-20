package com.rg.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

	@Override
	public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
        
    public static void main(String args[]) {

        //if (System.getSecurityManager() == null) {
            //System.setSecurityManager(new SecurityManager());
        //}
        
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 2002);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(2000);
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
