package KhanhKy.models;

import java.util.ArrayList;
import java.util.List;

public class Order {

    int tableNumber;
    int isTax;
    double tax;
    double grandTotal_beforeTax;
    double grandTotal;
    List<Food_Details> orderList = new ArrayList<Food_Details>();
    int count;
    
    
    
   
    public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getIsTax() {
		return isTax;
	}

	public void setIsTax(int isTax) {
		this.isTax = isTax;
	}

	public double getGrandTotal_beforeTax() {
		return grandTotal_beforeTax;
	}

	public void setGrandTotal_beforeTax(double grandTotal_beforeTax) {
		this.grandTotal_beforeTax = grandTotal_beforeTax;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public List<Food_Details> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Food_Details> itemList) {
		this.orderList = itemList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
	
	public void addAmountToOrderList(Food_Details item, int amount) {
		int loc = orderList.indexOf(item);
        if(loc == -1) {
        	item.increaseAmount(amount);
        	item.calculateSubTotal();
        	orderList.add(item);
            //this.count += amount;
        }
        else {
        	orderList.get(loc).increaseAmount(amount);
        	orderList.get(loc).calculateSubTotal();
        	//return true;
        }
           // return false;
	}

    public boolean addToOrderList(Food_Details item){

        if(orderList.add(item)) {
            this.count++;
            return true;
        }
        else
            return false;
    }
    
	public double getTax() {
        calculateTax();
        return tax;
    }
    
    public void calculateTax(){
        tax = grandTotal * 0.01;
    }


    public void CalculateTotal (){
        grandTotal = 0;
        double value;


        for(int i =0; i < orderList.size();i++){
            value = (orderList.get(i).getPrice())  * orderList.get(i).getAmount();
            grandTotal += value;
        }
        grandTotal_beforeTax = grandTotal;

        if(isTax==1) {
            grandTotal = grandTotal + (grandTotal * 0.01);
        }
    }
}
