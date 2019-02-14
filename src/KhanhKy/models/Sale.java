package KhanhKy.models;

public class Sale {
	int ID;
	int foodID;
	int orderID;
	double amount;
	int status;
	
	
	public Sale(int foodID, int orderID, double amount, int status) {
		super();
		this.foodID = foodID;
		this.orderID = orderID;
		this.amount = amount;
		this.status = status;
	}
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
	

}
