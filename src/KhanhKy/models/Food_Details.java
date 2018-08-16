package KhanhKy.models;

public class Food_Details {
	private String category;
	private String vietnameseCategory;
	private String vietnameseName;
	private String name;
	private double price;
	private Ingredient ingredient;	// Add constructor for this later
	
	public Food_Details(String category, String name, double price) {
		super();
		this.category = category;
		this.name = name;
		this.price = price;
	}
	
	public Food_Details(String name, String vietnameseName, String category, String vietnameseCategory, double price) {
		super();
		this.category = category;
		this.vietnameseCategory = vietnameseCategory;
		this.vietnameseName = vietnameseName;
		this.name = name;
		this.price = price;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVietnameseCategory() {
		return vietnameseCategory;
	}
	public void setVietnameseCategory(String vietnameseCategory) {
		this.vietnameseCategory = vietnameseCategory;
	}
	public String getVietnameseName() {
		return vietnameseName;
	}
	public void setVietnameseName(String vietnameseName) {
		this.vietnameseName = vietnameseName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
}
