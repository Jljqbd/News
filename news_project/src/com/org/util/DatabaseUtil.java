package com.org.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
	private static String driver = ConfigManager.getProperty("driver");
	private static String url = ConfigManager.getProperty("url");
	private static String user = ConfigManager.getProperty("user");
	private static String password = ConfigManager.getProperty("password");

	static {
		try {
			// System.out.println("到这了");
			Class.forName(driver);
			// System.out.println("?");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		// Statement stmt = null;
		try {
			//Class.forName(driver);

			// 打开链接
			//System.out.println("连接数据库...");
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		} finally {

		}
		return conn;
	}
	// return conn;

	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
