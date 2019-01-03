package KhanhKy.models;

public class Sale {
	int ID;
	int foodID;
	int orderID;
	int status;
	
	
	public Sale(int foodID, int orderID, int status) {
		super();
		this.foodID = foodID;
		this.orderID = orderID;
		this.status = status;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getFoodID() {
		return foodID;
	}
	public void setFoodID(int foodID) {
		this.foodID = foodID;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
