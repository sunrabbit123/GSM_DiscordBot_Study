package com.github.sunrabbit123.Liechu_bot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Information {
	static String getInformation(String path) {
		String prv = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			prv = br.readLine();
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return prv;
		
	}
}


public class CommandManager {
	Connection con = null;
	
	CommandManager(){
		String path = "./account.txt";
		String[] information = Information.getInformation(path).split("!!!");
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = information[0];
		String password = information[1];
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
			
		
	}
	
	public String SelectCommand(String msg) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "select * from (select command_value from Lie_chu where (command_key like ? ) order by DBMS_RANDOM.VALUE) where rownum <= 1";
		
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, msg);
			rs = ps.executeQuery();

			if(rs.next()) {
				return rs.getString(1);
			}else {
				return "¸Ï¾Æ,,,?";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ÀÌÃò,,,?";

	}
	
	public String InsertCommand(String server, String key, String value, String publisher) {
		PreparedStatement ps = null;
		String sql = "insert into lie_chu values(COMMAND_SEQUENCE.nextval, ?, ?, ?, ?)";
		
		try {
			ps = this.con.prepareStatement(sql);
			ps.setString(1, server);
			ps.setString(2, key);
			ps.setString(3, value);
			ps.setString(4, publisher);
			if(ps.executeUpdate() >= 1) {
				return "¸Ï¾Æ¤¿¤¿¤¿¤¿";
			} else {
				return "½ÇÆÐÇß¸Ï....";
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "ÀÌ°Ô ¹»±î";
		
	}
	
	public String DeleteCommand(String key){
		PreparedStatement ps = null;
		String sql = "delete from lie_chu where command_key like ?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, key);
			
			if(ps.executeUpdate() >= 1) {
				return "¸Ï¾Æ¤¿¤¿¤¿¤¿";
			} else {
				return "½ÇÆÐÇß¸Ï....";
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "ÀÌ°Ô ¹»±î";
			
	}
}
