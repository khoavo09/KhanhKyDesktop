package KhanhKy;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cashier extends JFrame implements ActionListener{

	List<String> foodCategories = new ArrayList<String>();
	//String foodCategories[] = {"Bò", "Heo", "Heo Rừng", "Gà" , "Mực", "Tôm", "Cá", "Lươn", "Ếch", "Dôm", "Bồ Câu", "Chim sẽ"};
	String foodCategoriesEN[]= {"Bo", "Heo", "Heo Rung", "Ga", "Muc", "Tom", "Ca", "Luon", "Ech", "Dom", "Bo_Cau", "Chim_se"};
	String beefFood[] = {"Bo Luc Lac", "Bo Xao"};
	String porkFood[]= {"Heo Nuong", "Heo Quay"};
	String chickenFood[] = {"Ga Nuong", "Ga Kho"};
	
	private JPanel contentPane;
	private JPanel buttonPanel = new JPanel();
	private JPanel btnDetailsPanel = new JPanel();
	private  JPanel orderPanel = new JPanel();
	private  JPanel editAmountPanel = new JPanel();
	
	DBConnect connect;


	//List<Order>  order = new ArrayList<Order>();
	Order myOrder = new Order();
	List<Food_Details> newSaleInOrder = new ArrayList<Food_Details>();
	List<JTextField> AmountJTextFields = new ArrayList<JTextField>();
	int orderID = 0;
	String areaName;
	int tableNum;

	List<Food_Details>  allFood = new ArrayList<Food_Details>();
	private final JLabel orderDetailsLabel = new JLabel("Order Details");
	private JTextArea orderDetailsTxt = new JTextArea();
	private final JButton submitButton = new JButton("Tinh Tien");
	private String receiptToPrint;
	
    private PrinterInstance mPrinter;

    int k= 0;
	int linesBefore=2;
    boolean needSpace;




	/**
	 * Create the frame.
	 */
	public Cashier(String areaName, int tableNum) {
		this.areaName = areaName;
		this.tableNum = tableNum;

		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(new File("Inconsolata-Regular.ttf"))).deriveFont(Font.PLAIN,15);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		// add first item to the array, so the starting item at 1 which matches with id from db
		foodCategories.add("no");
		allFood.add(new Food_Details());
		connect = new DBConnect();
		allFood = connect.getMenuData();
		orderID = connect.getActiveOrder(areaName, tableNum);
		foodCategories = connect.getDistinctData("viet_category", "food");
		JButton categoryButtons[] = new JButton[foodCategories.size()];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 820);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		buttonPanel.setBounds(0, 6, 360, 750);
		contentPane.add(buttonPanel);
		
		btnDetailsPanel.setBounds(370, 6, 350, 666);

		
		for(int i=1; i < categoryButtons.length; i++) {
			JButton btn = new JButton(foodCategories.get(i));
			btn.setText(foodCategories.get(i));
			btn.setActionCommand(foodCategories.get(i)+"Btn");
			btn.addActionListener(this);
			buttonPanel.add(btn);
		}
		
		
		//buttonPanel.add(btnNewButton);
		contentPane.add(btnDetailsPanel);
		//orderPanel.setBounds(630, 6, 450, 666);
	    JScrollPane scroll = new JScrollPane ( orderDetailsTxt );
	    scroll = new JScrollPane(orderDetailsTxt);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(750, 106, 400, 566);
		



		
		contentPane.add(scroll);
		orderPanel.setLayout(null);
		orderDetailsLabel.setBounds(110, 71, 83, 16);
		editAmountPanel.setLayout(new GridLayout(0,2,3,2));
		editAmountPanel.setBounds(1150,152,70,0);
		contentPane.add(editAmountPanel);
		
		
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
		submitButton.setActionCommand("Tinh Tien");
		submitButton.addActionListener(this);
		//orderPanel.add(orderDetailsTxt);
		
		JButton backButton = new JButton("Quay Lai");
		backButton.setBounds(699, 6, 156, 59);
		contentPane.add(backButton);
		backButton.setActionCommand( "Go Back");
		backButton.addActionListener(this);
		
		
		
		
		if(orderDetailsTxt.getText().equals("")) {
    		List<Integer> foodIDinOrder = connect.getOrderDetails(orderID);
    		System.out.println("FOOD ID"+foodIDinOrder);
    		for(int i=0; i< foodIDinOrder.size();i++) {
    			myOrder.addAmountToOrderList(allFood.get(foodIDinOrder.get(i)),1, "inc");
    		}
        
    		needSpace = false;

    		AmountJTextFields.clear();
		printOrderOnScreen();
		addEditTextField();

    	}
	}
	
	public void actionPerformed(ActionEvent e){
	    String a = e.getActionCommand();
	    System.out.println("a"+a);
		List<Food_Details>  tempFood = new ArrayList<Food_Details>();
		List<String> DishesInSelectedCategory = new ArrayList<String>();

		// Add the buttons on the left and middle panels
	    for(int i=0;i < foodCategories.size();i++) {
	    		if(a.equals(foodCategories.get(i) + "Btn")) {
	    			addButtons(foodCategories.get(i));
	    			//DishesInSelectedCategory = getDishesInTheCategory(foodCategories.get(i));
	    		}
	    }

	    if(a.equals("1Field")) {
	    	System.out.println("I GOT IT");
	    }
	    
	    if(a.equals("Go Back")) {
	    	SelectTable selectTable = new SelectTable();
	    	selectTable.setVisible(true);
            List<Food_Details> localOrder = myOrder.getOrderList();
            List<Sale> saleList = new ArrayList<Sale>();

            for(int i=0; i < newSaleInOrder.size();i++) {
            	Sale sale = new Sale(newSaleInOrder.get(i).getId(),orderID,0);
            	saleList.add(sale);
            }
            connect.insertSale(saleList);

	    	dispose();
	    }
	    
	    else if(a.equals("Tinh Tien")) {
	    	connect.closeOrder(orderID, areaName, tableNum);
	    	SelectTable selectTable = new SelectTable();
	    	selectTable.setVisible(true);
	    	dispose();
            //CanvasPrint cp = new CanvasPrint();
	    	
			// PRINT RECEIPT OUT

//			PrinterService printerService = new PrinterService();
//			System.out.println(printerService.getPrinters());
   // 		printerService.printString("Caysn Thermal Printer", receiptToPrint);
    		 
    		// cut that paper!
    		//byte[] cutP = new byte[] { 0x1d, 'V', 1 };
     
    		//printerService.printBytes("Caysn Thermal Printer", cutP);


	    }
	    else {
	    	
	    
    	int index=0;

    	// When the user click on an item on the menu
    	while(index < allFood.size()) {
    		if(a.equals(allFood.get(index).getVietnameseName())){
    			String clickedItem = allFood.get(index).getVietnameseName();
    			myOrder.addAmountToOrderList(allFood.get(index),1,"inc");
    			newSaleInOrder.add(allFood.get(index));
    			printOrderOnScreen();
    			addEditTextField();
		    	break;
    		}
	    	index++;
		 }
	    }
    	
//		tempFood = order.getOrderList();
//    	for(int index=0;index< order.getCount();index++) {
//    		System.out.println(tempFood.get(index).getName());
//    	}
	    
	}
	
	
	private void printOrderOnScreen() {
		orderDetailsTxt.setText("");
		//orderPanel.removeAll();
		//orderDetailsTxt.removeAll();
		//orderDetailsTxt.append(allFood.get(index).getName() + "  " + allFood.get(index).getPrice() + "\n");
		//myOrder.addToOrderList(allFood.get(index));
		
//		Charset utf8 = Charset.forName("UTF-8");
//        Charset def = Charset.defaultCharset();
//
//        String charToPrint = "Bánh Tráng Mắm Ổi";
//        String message="";
//
//        byte[] bytes;
//		try {
//			bytes = charToPrint.getBytes("UTF-8");
//	        message = new String(bytes , def.name());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int j = 0;
		
        List<Food_Details> localOrder = myOrder.getOrderList();
        myOrder.CalculateTotal();
        //orderDetailsTxt.append(message);
        orderDetailsTxt.append(String.format("%-4s%-28s%5s%5s%10s", "STT","Ten Hang", "DG", "SL", "T.Tien\n\n"));
        for(int i=0; i < localOrder.size();i++)
        	orderDetailsTxt.append(String.format("%-4d%-28s%5.1f%5.1f%8.1f\n",i+1, localOrder.get(i).getVietnameseName(),
            localOrder.get(i).getPrice(),localOrder.get(i).getAmount(),localOrder.get(i).getSubTotal()));


        

        orderDetailsTxt.append("-----------------------------------------------\n");
        if(myOrder.getIsTax() == 1) {
        	orderDetailsTxt.append(String.format("%25s %22.1f\n","Cong:", myOrder.getGrandTotal_beforeTax()));
        	orderDetailsTxt.append(String.format("%25s %22.1f\n","Thue:", myOrder.getTax()));
        }

        orderDetailsTxt.append(String.format("%25s %22.1f\n","Tổng Cộng:", myOrder.getGrandTotal()));
		
        receiptToPrint = orderDetailsTxt.getText();
    	orderPanel.repaint();
    	orderPanel.revalidate();

	}
	
	private void addEditTextField() {
        List<Food_Details> localOrder = myOrder.getOrderList();

        if(k==1) {
        	editAmountPanel.removeAll();
        }
        
        
        //System.out.println("amount"+ AmountJTextFields.size());
       // System.out.println(localOrder.size());

        if(AmountJTextFields.size() == 0 && localOrder.size() > 0) {
        	System.out.println("INSIDE");
			for(int i=0;i<localOrder.size();i++) {
    				editAmountPanel.setBounds(1150,142,70, (i+1) * 16);
	            JTextField editAmountTextField = new JTextField(Double.toString(localOrder.get(i).getAmount()));
	            AmountJTextFields.add(editAmountTextField);
	            JButton deleteBtn = new JButton("X");
	            deleteBtn.setActionCommand( i + "Btn");
	            deleteBtn.addActionListener(this);
	            editAmountTextField.setName(Integer.toString(i));
	            

	            editAmountTextField.getDocument().addDocumentListener(new DocumentListener() {

						@Override
						public void insertUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							warn();
							
						}
						@Override
						public void removeUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							//warn();
							
						}
						@Override
						public void changedUpdate(DocumentEvent e) {
							warn();
							// TODO Auto-generated method stub
							
						}
						public void warn() {
//		            		     if (Double.parseDouble(editAmountTextField.getText())<=0){
//		            		       JOptionPane.showMessageDialog(null,
//		            		          "Error: Please enter number bigger than 0", "Error Massage",
//		            		          JOptionPane.ERROR_MESSAGE);
//		            		     }
							 int foodID = -1;
		            		     int loc = -1;
		            		     double amount =0;
		            		     try {
			            		     loc = Integer.parseInt(editAmountTextField.getName());
			            		     amount = Double.parseDouble(editAmountTextField.getText());
		            		     }
		            		     //fix this later
		            		     catch (NumberFormatException e){
			            		      JOptionPane.showMessageDialog(null,"Error: Wrong format", "Error Massage",
		            		          JOptionPane.ERROR_MESSAGE);
		            		     }
		            		     foodID = localOrder.get(loc).getId();
		             		myOrder.addAmountToOrderList(allFood.get(foodID),amount, "set");
		             		printOrderOnScreen();
		            		    System.out.println(localOrder.get(loc).getAmount());
		            		  }
	            		});
	            editAmountPanel.add(editAmountTextField);
	            editAmountPanel.add(deleteBtn);
	            linesBefore = orderDetailsTxt.getLineCount();
	            k=i+1;
	            needSpace=true;
			}
		}
        else if(linesBefore != orderDetailsTxt.getLineCount()) {
        		if(needSpace) {
        			editAmountPanel.setBounds(1130,142,70, 16 + k * 16);
        		}
        		else {
        			editAmountPanel.setBounds(1130,142,70, k * 16);
        		}
            editAmountPanel.repaint();
            editAmountPanel.revalidate();
            System.out.println(k);
            JTextField editAmountTextField = new JTextField("");
            editAmountTextField.setActionCommand(k +"Field");
            editAmountTextField.addActionListener(this);
            AmountJTextFields.add(editAmountTextField);
            if(!localOrder.isEmpty()) {
        			editAmountTextField.setText("1.0");
            }
            JButton deleteBtn = new JButton("X");
            deleteBtn.setActionCommand( k + "Btn");
            deleteBtn.addActionListener(this);
            editAmountPanel.add(editAmountTextField);
            editAmountPanel.add(deleteBtn);
            linesBefore = orderDetailsTxt.getLineCount();
        		k++;
        }
        else {
        		if(!localOrder.isEmpty()) {
        			for(int i=0;i<localOrder.size();i++) {
        				AmountJTextFields.get(i).setText(Double.toString(localOrder.get(i).getAmount()));
        			}
        		}
        }
	}
	

	// Add buttons to the middle panel
	private void addButtons(String target) {
		btnDetailsPanel.removeAll();
		//List<Food_Details>  foodInCategory = new ArrayList<Food_Details>();
		for(int i =1; i < allFood.size();i++) {
			if(allFood.get(i).getVietnameseCategory().equals(target)) {
				JButton btn = new JButton();
				btn.setText(allFood.get(i).getVietnameseName());
				btn.setActionCommand( allFood.get(i).getVietnameseName());
				btn.addActionListener(this);
				btnDetailsPanel.add(btn);
				//foodInCategory.add(allFood.get(i));
			}
		}
		btnDetailsPanel.repaint();
		btnDetailsPanel.revalidate();
	}
	
	
}
