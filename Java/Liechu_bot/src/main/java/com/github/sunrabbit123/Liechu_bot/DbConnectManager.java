package com.github.sunrabbit123.Liechu_bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnectManager {

	public static Connection makeCon() {
		// TODO Auto-generated method stub
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "SUNRABBIT";
		String password = "1163";
		Connection con = null;
		
		
			try {
				Class.forName(driver);
				con=DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			System.out.println("연결 성공");
			
			
		return con;
		
	}

}
