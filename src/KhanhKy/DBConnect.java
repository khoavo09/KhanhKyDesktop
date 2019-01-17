package KhanhKy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import KhanhKy.models.Food_Details;
import KhanhKy.models.Sale;
import KhanhKy.models.Table;

public class DBConnect {
	
	private Connection con;
	private Statement st;
	private ResultSet results;
	
	
	// Connect to mysql database
	public DBConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khanhkydatabase?serverTimezone=UTC&useSSL=false", "root", "12345678");
			st = con.createStatement();
		}
		catch(Exception err){
			System.out.println("Error: " + err);
			
		}
	}
	
	// Retrieve data
	public List<Food_Details> getMenuData() {
		List<Food_Details> allFood= new ArrayList<Food_Details>();
		allFood.add(new Food_Details(0,"null", "null", "null","null", -1 ));
		try {
			String query = "select * from food";
			results = st.executeQuery(query);
			while(results.next()) {
				int id = results.getInt("id");
				String name = results.getString("name");
				String viet_name = results.getString("viet_name");
				String category = results.getString("category");
				String viet_category = results.getString("viet_category");
				double price = results.getDouble("price");
				Food_Details foodDetail = new Food_Details(id,name, viet_name, category, viet_category, price);
				allFood.add(foodDetail);
				//System.out.println(foodDetail.getVietnameseName());
			}			
		}
		catch(Exception err) {
			System.out.println(err);
		}
		return allFood;
	}
	
	
	public List<Table> getTableData() {
		List<Table> allTables= new ArrayList<Table>();
		try {
			String query = "select * from all_tables";
			results = st.executeQuery(query);
			while(results.next()) {
				int id = results.getInt("id");
				String area = results.getString("area");
				int number = results.getInt("number");
				int status = results.getInt("status");
				Table table = new Table(id, area, number, status);
				allTables.add(table);
				//System.out.println(foodDetail.getVietnameseName());
			}			
		}
		catch(Exception err) {
			System.out.println(err);
		}
		return allTables;
	}
	
	public List<String> getDistinctData(String column, String table) {
		List<String> list= new ArrayList<String>();

		try {
			String query = "select DISTINCT " + column +" from "+ table;
			results = st.executeQuery(query);
			while(results.next()) {
				String holder = results.getString(column);
				list.add(holder);
			}			
		}
		catch(Exception err) {
			System.out.println(err);
		}
		return list;
	}
	
	public int getActiveOrder(String area, int number) {
		try {
			
			// get the id
			// if occupied -> search for that table id in order
			// if empty -> set to occupied
			//		add new order id for that table id
			
			String query = "SELECT * from all_tables where area = '" +area +"' AND number ='" +number+ "'";
			results =  st.executeQuery(query);
			int activeOrderID= -1;
			
			if(results.next()) {
				// 0 means empty
				if(results.getInt("status") == 0) {
					String sql = "INSERT INTO all_orders(table_id) VALUES(?)";
			        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			        statement.setInt(1, results.getInt("id"));
					System.out.println(statement);

			        
			        int affectedRows = statement.executeUpdate();

			        if (affectedRows == 0) {
			            throw new SQLException("Creating order failed, no rows affected.");
			        }

			        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			            	long temp = generatedKeys.getLong(1);
			            	activeOrderID = (int)temp;
					        sql = "UPDATE all_tables set status = 1 where id=" + results.getInt("id");
							st.executeUpdate(sql);
			            	//activeOrderID = generatedKeys.getLong(1);
			            	//System.out.println("Active: "+activeOrderID);
			            	return activeOrderID;
			            }
			            else {
			                throw new SQLException("Creating order failed, no ID obtained.");
			            }
			        }
				}
				else {
					int tableID = results.getInt("id");
					try {
						query = "SELECT * from all_orders where table_id='" + tableID + "' and status=0";
						results = st.executeQuery(query);
						while(results.next()) {
							activeOrderID = results.getInt("id");
							System.out.println(activeOrderID);
							return activeOrderID;
						}		
					}
					catch(Exception err) {
						System.out.println(err);
					}
				}

			}

			
			
//			if(!results.next()) {
//				String sql = "INSERT INTO all_orders(table_id) VALUES( "+results.getInt("id") + " )";
//				System.out.println("EMPTY AF");
//			}
//			else {
//	            do {
//					System.out.println(results.getInt("id"));
//	            } while (results.next());
//			}

//			return results.getInt("id");


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public List<Integer> getOrderDetails(int orderID) {
		List<Integer> salesInTheOrder= new ArrayList<Integer>();
		try {
			String query = "select * from sale where order_id = "+orderID;
			results = st.executeQuery(query);
			while(results.next()) {
				int id = results.getInt("food_id");
				salesInTheOrder.add(id);
				//System.out.println(foodDetail.getVietnameseName());

			}			
		}
		catch(Exception err) {
			System.out.println(err);
		}
		return salesInTheOrder;
	}
	
	public boolean insertSale(List<Sale> saleList) {
		String query = "INSERT INTO sale(food_id,order_id, status) " + "VALUES(?,?,?)";

		try {
			PreparedStatement statement = con.prepareStatement(query);
			int count = 0;
			 
            for (Sale sale : saleList) {
                statement.setInt(1, sale.getFoodID());
                statement.setInt(2, sale.getOrderID());
                statement.setInt(3, sale.getStatus());
                statement.addBatch();
                count++;
                // execute every 50 rows or less
                if (count % 50 == 0 || count == saleList.size()) {
                    statement.executeBatch();
                }		
            }
		}
		catch(Exception err) {
			System.out.println(err);
		}
		return false;
	}
	
	public void closeOrder(int orderID, String areaName, int tableNum) {
		try {
			String query = "update all_orders set status = 1 where id='" + orderID +"'";
			st.executeUpdate(query);	
			query = "update all_tables set status = 0 where area='" + areaName +"' and number='" +tableNum+ "'";
			st.executeUpdate(query);	
		}
		catch(Exception err) {
			System.out.println(err);
		}
	}
	
}
