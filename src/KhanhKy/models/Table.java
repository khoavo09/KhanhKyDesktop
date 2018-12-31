package KhanhKy.models;

public class Table {
	int id;
	String area;
	int number;
	int status;
	
	
	public Table(int id, String area, int number, int status) {
		super();
		this.id = id;
		this.area = area;
		this.number = number;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
