package com.atmecs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MysqlConnection {

	public String readTestdataFromMysql(String query, int columnNo) throws Exception {
		String data = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/assessmenttwo?autoReconnect=true&useSSL=false&user=root&password=admin@123");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			data = rs.getString(columnNo);
		}
		con.close();
		return data;
	}
}
