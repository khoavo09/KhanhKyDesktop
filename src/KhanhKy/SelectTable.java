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

public class SelectTable extends JFrame implements ActionListener{

	private JPanel contentPane;

	private JPanel areaPanel = new JPanel();
	private JPanel areaDetailsPanel = new JPanel();
	private final JPanel orderPanel = new JPanel();
	
	
	String areaCategories[] = {"Sảnh", "Ổi", "Mít", "Ao"};



	//List<Order>  order = new ArrayList<Order>();
	Order myOrder = new Order();

	List<Food_Details>  allFood = new ArrayList<Food_Details>();
	private final JLabel orderDetailsLabel = new JLabel("Order Details");
	private JTextArea orderDetailsTxt = new JTextArea();
	private final JButton submitButton = new JButton("Tinh Tien");
	
	/**
	 * Create the frame.
	 * 
	 */
	public SelectTable() {

		JButton categoryButtons[] = new JButton[areaCategories.length];
		this.allFood = allFood;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		areaPanel.setBounds(0, 6, 129, 666);
		contentPane.add(areaPanel);
		
		areaDetailsPanel.setBounds(190, 6, 406, 666);

		
		for(int i=0; i < categoryButtons.length; i++) {
			JButton btn = new JButton(areaCategories[i]);
			btn.setText(areaCategories[i]);
			btn.setActionCommand(areaCategories[i]+"Btn");
			btn.addActionListener(this);
			areaPanel.add(btn);
		}
		
		
		//buttonPanel.add(btnNewButton);
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
	    String a = e.getActionCommand();
		List<Food_Details>  tempFood = new ArrayList<Food_Details>();

		// Add the buttons on the left and middle panels
	    if(a.equals("SảnhBtn")){
			addButtons("Sảnh");
		}
	    if(a.equals("ỔiBtn")){
			addButtons("Ổi");	
	    }
	    if(a.equals("MítBtn")){
			addButtons("Mít");	   
	    }
	    
	    if(a.equals("Go Back")) {
	    	
	    }
	  
	    
    	int index=0;

    	// When the user click on an item on the menu
    	while(index < 4) {
    		for(int k=0;k<4;k++) {
        		if(a.equals(areaCategories[index] +" "+ k + "Btn")){
        			System.out.println(a);
    		    	orderPanel.repaint();
    		    	orderPanel.revalidate();
        		}
    		}

	    	index++;
		 }
	}

	
	private void addButtons(String target) {
		areaDetailsPanel.removeAll();
		//List<Food_Details>  foodInCategory = new ArrayList<Food_Details>();
		for(int i =0; i < 4;i++) {
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
}
