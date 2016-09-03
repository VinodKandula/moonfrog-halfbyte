package com.aws.sns.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.common.exception.MoonfrogAppRuntimeException;

public class DBConnector {
	private static final Log LOGGER = LogFactory.getLog(DBConnector.class);
	
	/*private static String dbName = System.getProperty("RDS_DB_NAME");
	private static String userName = System.getProperty("RDS_USERNAME");
	private static String password = System.getProperty("RDS_PASSWORD");
	private static String hostname = System.getProperty("RDS_HOSTNAME");
	private static String port = System.getProperty("RDS_PORT");

	private static String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port
			+ "/" + dbName + "?user=" + userName + "&password=" + password;*/

	private static DBConnector _INSTANCE = new DBConnector();

	private DBConnector() {
	}

	public static DBConnector getInstance() {
		return _INSTANCE;
	}

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/moonfrog";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "";
	static {
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e);
		}
	}

	@Deprecated
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException se) {
			LOGGER.error(se.getMessage());
			throw new MoonfrogAppRuntimeException(se.getMessage());
		}
		return conn;
	}
	private static Context initContext = null;
	private static DataSource ds = null;
	
	static {
		connectionPoolSetup();
	}
	
	private static void connectionPoolSetup() {
		try {
			initContext = new InitialContext();
			ds = (DataSource) initContext.lookup( "java:/comp/env/jdbc/moonfrog" );
		} catch (NamingException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public static Connection getPooledConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public static void close(ResultSet... rs) {
		for (ResultSet resultSet : rs) {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (SQLException se) {
			}// nothing we can do
		}
	}
	
	public static void close(Statement... statements) {
		for (Statement stmt : statements) {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			}// nothing we can do
		}
	}
	
	public static void close(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
		}// nothing we can do
	}

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, name FROM consumer";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String name = rs.getString("name");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Name: " + name);
				System.out.println();
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
	}// end main

}
