package KhanhKy;

import java.sql.*;

public class DBConnect {
	
	private Connection con;
	private Statement st;
	private ResultSet results;
	
	public DBConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khanhky?serverTimezone=UTC", "root", "123456");
			st = con.createStatement();
		}
		catch(Exception err){
			System.out.println("Error: " + err);
			
		}
	}
	
	public void getData() {
		try {
			String query = "select * from food";
			results = st.executeQuery(query);
			System.out.println("Records from Database");
			while(results.next()) {
				String name = results.getString("name");
				String category = results.getString("category");
				double price = results.getDouble("price");
				System.out.println(name + category + "price: " + price);
			}
			
		}
		catch(Exception err) {
			System.out.println(err);
		}
		
	}

}
