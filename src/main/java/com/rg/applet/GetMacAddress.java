package com.rg.applet;

import java.applet.Applet;
import java.awt.Graphics;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GetMacAddress extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5774258676676483336L;

	/*
	public void getMacAddress() {

		InetAddress ip;

		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	*/

	public void paint(Graphics g) {
		g.drawRect(0, 0, 350, 350);
		g.drawString("Hello World 2", 25, 50);
		

		InetAddress ip;
		String macAddress = "unknown now.";
		try {
				
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				
			byte[] mac = network.getHardwareAddress();
				
			System.out.print("Current MAC address : ");
				
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
			System.out.println(sb.toString());
			
			macAddress = sb.toString();
				
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
		} catch (SocketException e){
				
			e.printStackTrace();
				
		}
		
		g.drawString(macAddress, 30, 30);
		
	}
	
	public void init() {
		//paint();
	}
}
