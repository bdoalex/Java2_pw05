package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceFactory {


	private DataSourceFactory() {
		// This is a static class that should not be instantiated.
		// Here's a way to remember it when this class will have 2K lines and you come
		// back to it in 2 years
		throw new IllegalStateException("This is a static class that should not be instantiated");
	}

	/**
	 * @return a connection to the SQLite Database
	 * 
	 */
	public static Connection getDataSource() {
		try {
			String urlDb = "jdbc:sqlite:sqlite.db";
			return DriverManager.getConnection(urlDb);
		} catch (SQLException e) {
			return null;
		}
	}
}