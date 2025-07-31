package com.aurionpro.database;

public class Database {
	private static final Database INSTANCE = new Database();

	Connection connection = null;

	public Database() {
		if (INSTANCE != null) {
			throw new RuntimeException("use getInstance() method");
		}
	}

	public static getInstance() {
		return INSTANCE;
	}

	public void connect() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "admin#123");
			System.out.println("Connection to database has been established");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

	}
}
