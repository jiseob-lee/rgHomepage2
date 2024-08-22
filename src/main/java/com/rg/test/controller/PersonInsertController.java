package com.rg.test.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonInsertController {

	private final Logger logger = LogManager.getLogger(PersonInsertController.class);

	@Autowired
	private ApplicationContext appContext;
	
	@RequestMapping("/rg/personTestInsert.do")
	public void personTestInsert() {

		Connection con = null;
		
		PreparedStatement ps = null;
		
		String query = "insert into person (id, name, email, phone, company_name, department, company_phone, company_addr) values (?,?,?,?,?,?,?,?)";
		
		try {
			
			//DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
			DataSource ds = (DataSource)appContext.getBean("dbDataSourceMySQL");
			con = ds.getConnection();
			
			logger.debug("################################### con : " + con);
			//con = DBConnection.getConnection();
			con.setAutoCommit(false);
			
			logger.debug("################################### Integer.MAX_VALUE : " + Integer.MAX_VALUE);

			ps = con.prepareStatement(query);
			
			
			//for (int i=20000000; i<30000000; i++) {
			for (int i=3000001; i<=4000000; i++) {
			//for (int i=1111; i<11111; i++) {
				
				ps.setInt(1, i);
				ps.setString(2, Integer.valueOf(i).toString());
				ps.setString(3, Integer.valueOf(i).toString());
				ps.setString(4, Integer.valueOf(i).toString());
				ps.setString(5, Integer.valueOf(i).toString());
				ps.setString(6, Integer.valueOf(i).toString());
				ps.setString(7, Integer.valueOf(i).toString());
				ps.setString(8, Integer.valueOf(i).toString());
				
				ps.addBatch();
				
				//if (i % 1000000 == 0) {
				if (i % 200 == 0) {
					int[] result = ps.executeBatch();
					logger.debug(i + " : " + Arrays.toString(result));
					con.commit();
				}
			}
			
			int[] result = ps.executeBatch();
			logger.debug(Arrays.toString(result));
			
			con.commit();
			
			
			/*
			String name1 = "Pankaj";
			String name2 = "Pankaj Kumar"; // longer than column length
			String name3 = "Kumar";

			ps.setInt(1, 1);
			ps.setString(2, name1);
			ps.addBatch();

			ps.setInt(1, 2);
			ps.setString(2, name2);
			ps.addBatch();

			ps.setInt(1, 3);
			ps.setString(2, name3);
			ps.addBatch();

			int[] results = ps.executeBatch();

			con.commit();
			System.out.println(Arrays.toString(results));
			*/

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
