package KhanhKy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import KhanhKy.models.Food_Details;

public class DBConnect {
	
	private Connection con;
	private Statement st;
	private ResultSet results;
	
	
	// Connect to mysql database
	public DBConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khanhky?serverTimezone=UTC&useSSL=false", "root", "123456");
			st = con.createStatement();
		}
		catch(Exception err){
			System.out.println("Error: " + err);
			
		}
	}
	
	// Retrieve data
	public List<Food_Details> getMenuData() {
		List<Food_Details> allFood= new ArrayList<Food_Details>();

		try {
			String query = "select * from food";
			results = st.executeQuery(query);
			while(results.next()) {
				String name = results.getString("name");
				String viet_name = results.getString("viet_name");
				String category = results.getString("category");
				String viet_category = results.getString("viet_category");
				double price = results.getDouble("price");
				Food_Details foodDetail = new Food_Details(name, viet_name, category, viet_category, price);
				allFood.add(foodDetail);
				//System.out.println(foodDetail.getVietnameseName());

			}			
		}
		catch(Exception err) {
			System.out.println(err);
		}
		return allFood;
	}

}
