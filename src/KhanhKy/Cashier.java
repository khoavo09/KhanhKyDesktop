package KhanhKy;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.android.print.sdk.CanvasPrint;
import com.android.print.sdk.PrinterInstance;

import KhanhKy.models.Food_Details;
import KhanhKy.models.Order;
import KhanhKy.models.Sale;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class Cashier extends JFrame implements ActionListener{

	
	String foodCategories[] = {"Bò", "Heo", "Heo Rừng", "Gà" , "Mực", "Tôm", "Cá", "Lươn", "Ếch", "Dôm", "Bồ Câu", "Chim sẽ"};
	String foodCategoriesEN[]= {"Bo", "Heo", "Heo Rung", "Ga", "Muc", "Tom", "Ca", "Luon", "Ech", "Dom", "Bo_Cau", "Chim_se"};
	String beefFood[] = {"Bo Luc Lac", "Bo Xao"};
	String porkFood[]= {"Heo Nuong", "Heo Quay"};
	String chickenFood[] = {"Ga Nuong", "Ga Kho"};
	
	private JPanel contentPane;
	private JPanel buttonPanel = new JPanel();
	private JPanel btnDetailsPanel = new JPanel();
	private final JPanel orderPanel = new JPanel();
	
	DBConnect connect;


	//List<Order>  order = new ArrayList<Order>();
	Order myOrder = new Order();
	int orderID = 0;

	List<Food_Details>  allFood = new ArrayList<Food_Details>();
	private final JLabel orderDetailsLabel = new JLabel("Order Details");
	private JTextArea orderDetailsTxt = new JTextArea();
	private final JButton submitButton = new JButton("Tinh Tien");
	
    private PrinterInstance mPrinter;





	/**
	 * Create the frame.
	 */
	public Cashier(String areaName, int tableNum) {
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(new File("monaco.ttf"))).deriveFont(Font.PLAIN,14);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		connect = new DBConnect();
		allFood = connect.getMenuData();
		orderID = connect.getActiveOrder(areaName, tableNum);
		
		JButton categoryButtons[] = new JButton[foodCategories.length];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buttonPanel.setBounds(0, 6, 129, 666);
		contentPane.add(buttonPanel);
		
		btnDetailsPanel.setBounds(190, 6, 406, 666);

		
		for(int i=0; i < categoryButtons.length; i++) {
			JButton btn = new JButton(foodCategoriesEN[i]);
			btn.setText(foodCategories[i]);
			btn.setActionCommand(foodCategoriesEN[i]+"Btn");
			btn.addActionListener(this);
			buttonPanel.add(btn);
		}
		
		
		//buttonPanel.add(btnNewButton);
		contentPane.add(btnDetailsPanel);
		//orderPanel.setBounds(630, 6, 450, 666);
	    JScrollPane scroll = new JScrollPane ( orderDetailsTxt );
	    scroll = new JScrollPane(orderDetailsTxt);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(622, 106, 450, 566);


		
		contentPane.add(scroll);
		orderPanel.setLayout(null);
		orderDetailsLabel.setBounds(110, 71, 83, 16);
		
		orderPanel.add(orderDetailsLabel);
		orderDetailsTxt.setEditable(false);
		
		orderDetailsTxt.setBounds(6, 99, 438, 472);
		orderDetailsTxt.setFont(f);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		submitButton.setBounds(739, 684, 145, 59);
		
		contentPane.add(submitButton);
		//orderPanel.add(orderDetailsTxt);
		
		JButton backButton = new JButton("Quay Lai");
		backButton.setBounds(699, 6, 156, 59);
		contentPane.add(backButton);
		backButton.setActionCommand( "Go Back");
		backButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
	    String a = e.getActionCommand();
		List<Food_Details>  tempFood = new ArrayList<Food_Details>();

		// Add the buttons on the left and middle panels
	    if(a.equals("BoBtn")){
			addButtons("Bo");
		}
	    if(a.equals("HeoBtn")){
			addButtons("Heo");	
	    }
	    if(a.equals("Heo RungBtn")){
			addButtons("Heo Ruong");	   
	    }
	    
	    if(a.equals("Go Back")) {
	    	SelectTable selectTable = new SelectTable();
	    	selectTable.setVisible(true);
            List<Food_Details> localOrder = myOrder.getOrderList();
            List<Sale> saleList = new ArrayList<Sale>();

            for(int i=0; i < localOrder.size();i++) {
            	Sale sale = new Sale(localOrder.get(i).getId(),orderID,0);
            	saleList.add(sale);
            }
        	connect.insertSale(saleList);

	    	dispose();
	    }
	    
	    if(a.equals("Tinh Tien")) {
            mPrinter.init();
            //CanvasPrint cp = new CanvasPrint();
            mPrinter.printText("Hello World");


	    }
	  
	    
    	int index=0;

    	// When the user click on an item on the menu
    	while(index < allFood.size()) {
    		if(a.equals(allFood.get(index).getName())){
    			orderDetailsTxt.setText("");
    			String clickedItem = allFood.get(index).getName();
				//orderPanel.removeAll();
    			orderDetailsTxt.removeAll();
    			orderDetailsTxt.append(allFood.get(index).getName() + "  " + allFood.get(index).getPrice() + "\n");
    			//myOrder.addToOrderList(allFood.get(index));
    			myOrder.addAmountToOrderList(allFood.get(index),1);
    			
                List<Food_Details> localOrder = myOrder.getOrderList();
                myOrder.CalculateTotal();
                orderDetailsTxt.append(String.format("%-5s%-20s%5s%4s%10s", "STT","Ten Hang", "DG", "SL", "T.Tien\n\n"));
                for(int i=0; i < localOrder.size();i++)
                	orderDetailsTxt.append(String.format("%-5d%-20s%5.1f%4d%9.1f\n",i+1, localOrder.get(i).getName(),
                    localOrder.get(i).getPrice(),localOrder.get(i).getAmount(),localOrder.get(i).getSubTotal()));

                orderDetailsTxt.append("--------------------------------------------\n");
                if(myOrder.getIsTax() == 1) {
                	orderDetailsTxt.append(String.format("%25s %17.1f\n","Cong:", myOrder.getGrandTotal_beforeTax()));
                	orderDetailsTxt.append(String.format("%25s %17.1f\n","Thue:", myOrder.getTax()));
                }

                orderDetailsTxt.append(String.format("%25s %17.1f\n","Tong Cong:", myOrder.getGrandTotal()));
    			
    			
		    	orderPanel.repaint();
		    	orderPanel.revalidate();
		    	break;
    		}
	    	index++;
		 }
    	
    	
//		tempFood = order.getOrderList();
//    	for(int index=0;index< order.getCount();index++) {
//    		System.out.println(tempFood.get(index).getName());
//    	}
	    
	}

	// Add buttons to the middle panel
	private void addButtons(String target) {
		btnDetailsPanel.removeAll();
		//List<Food_Details>  foodInCategory = new ArrayList<Food_Details>();
		for(int i =0; i < allFood.size();i++) {
			if(allFood.get(i).getCategory().equals(target)) {
				JButton btn = new JButton();
				btn.setText(allFood.get(i).getVietnameseName());
				btn.setActionCommand( allFood.get(i).getName());
				btn.addActionListener(this);
				btnDetailsPanel.add(btn);
				//foodInCategory.add(allFood.get(i));
			}
		}
		btnDetailsPanel.repaint();
		btnDetailsPanel.revalidate();
	}
}
