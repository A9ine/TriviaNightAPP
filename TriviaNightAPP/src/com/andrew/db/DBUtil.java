package com.andrew.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection connection = null;
	public static Connection getConnection() {
		if(connection!=null) {
			try {
				if (!connection.isClosed()) {
					return connection;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
			System.out.println("success");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	
	public static Connection getDbConnection() {
		Connection dnconnection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			dnconnection = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
			System.out.println("success");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dnconnection;
	}
	
	public static void closeConnection() {
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String [] args) throws SQLException {
		Connection conn = getConnection();
		System.out.println(conn.isClosed());
		closeConnection();
		System.out.println(conn.isClosed());
	}
}
