package KhanhKy;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import KhanhKy.models.Food_Details;
import KhanhKy.models.Order;
import KhanhKy.models.Table;

public class SelectTable extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JFrame frame;
	private JPanel areaPanel = new JPanel();
	private JPanel areaDetailsPanel = new JPanel();
	private final JPanel orderPanel = new JPanel();
	
	
	//String areaCategories[] = {"Sảnh", "Ổi", "Mít", "Ao"};

	List<String> areaCategories;


	//List<Order>  order = new ArrayList<Order>();
	Order myOrder = new Order();

	List<Table>  allTables = new ArrayList<Table>();

	private int maxNumOfTables =0;
	private String selectedCategory="$$%$";


	private final JLabel orderDetailsLabel = new JLabel("Order Details");
	private JTextArea orderDetailsTxt = new JTextArea();
	private final JButton submitButton = new JButton("Tinh Tien");
	
	/**
	 * Create the frame.
	 * 
	 */
	public SelectTable() {

		
		DBConnect connect = new DBConnect();
		allTables = connect.getTableData();
		areaCategories = connect.getDistinctData("area", "all_tables");
		
		JButton categoryButtons[] = new JButton[areaCategories.size()];

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		areaPanel.setBounds(0, 6, 129, 666);
		contentPane.add(areaPanel);
		
		areaDetailsPanel.setBounds(190, 6, 406, 666);

		
		for(int i=0; i < areaCategories.size(); i++) {
			JButton btn = new JButton(areaCategories.get(i));
			btn.setText(areaCategories.get(i));
			btn.setActionCommand(areaCategories.get(i)+"Btn");
			btn.addActionListener(this);
			areaPanel.add(btn);
		}
		
		
		contentPane.add(areaDetailsPanel);
		//orderPanel.setBounds(630, 6, 450, 666);


		
		orderPanel.setLayout(null);
		orderDetailsLabel.setBounds(110, 71, 83, 16);
		
		orderPanel.add(orderDetailsLabel);
		orderDetailsTxt.setEditable(false);
		
		orderDetailsTxt.setBounds(6, 99, 438, 472);
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
	    String clickedButtonStr = e.getActionCommand();
		List<Food_Details>  tempFood = new ArrayList<Food_Details>();

		// Add the buttons on the left and middle panels
		
		
		int i =0;
		
		//System.out.println("Clicked: "+clickedButtonStr);
		//System.out.println("Selected: "+selectedCategory +"Btn");

		if(clickedButtonStr.contains(selectedCategory)) {
	    	int index=0;

	    	boolean found= false;
	    	
			do {
				if(clickedButtonStr.equals(selectedCategory+" "+(index+1)+"Btn")) {        		
        			System.out.println(clickedButtonStr);
    		    	orderPanel.repaint();
    		    	orderPanel.revalidate();
    				Cashier cashier = new Cashier(selectedCategory, index+1);
    				cashier.setVisible(true);
    				dispose();
					found = true;
				}
    			//System.out.println("LOOPING" +areaCategories.get(index)+" "+(index+1)+"Btn");

				index++;
			}
			while(index< areaCategories.size() && found == false);

		}
		else {    
			boolean found = false;
			do {
				if(clickedButtonStr.equals(areaCategories.get(i)+"Btn")) {
					maxNumOfTables = getTheHighest(areaCategories.get(i));
					addButtons(areaCategories.get(i),maxNumOfTables);
					selectedCategory = areaCategories.get(i);
					found = true;
				}
				i++;
			}
			while(i< areaCategories.size() && found == false);
			
		}
		
	
	    // TODO: implement back button
	    if(clickedButtonStr.equals("Go Back")) {
	    	
	    }
	  
	    

		 }
//	}

	
	private void addButtons(String target, int size) {
		areaDetailsPanel.removeAll();
		for(int i =1; i <= size;i++) {
				JButton btn = new JButton();
				btn.setText(target+ " "+ i);
				btn.setActionCommand( target+ " "+ i + "Btn");
				btn.addActionListener(this);
				areaDetailsPanel.add(btn);

				//foodInCategory.add(allFood.get(i));
		}
		areaDetailsPanel.repaint();
		areaDetailsPanel.revalidate();
	}
	
	private int getTheHighest(String target) {
		int highest = 0;
		for (int i=0; i< allTables.size();i++) {
			if(allTables.get(i).getArea().equals(target) && allTables.get(i).getNumber() > highest )
				highest = allTables.get(i).getNumber();
		}
		return highest;
	}
}
