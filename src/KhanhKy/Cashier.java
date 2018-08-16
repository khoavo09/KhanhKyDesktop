package KhanhKy;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import KhanhKy.models.Food_Details;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Cashier extends JFrame implements ActionListener{

	
	String foodCategories[] = {"Bò", "Heo", "Heo Rừng", "Gà" , "Mực", "Tôm", "Cá", "Lươn", "Ếch", "Dôm", "Bồ Câu", "Chim sẽ"};
	String foodCategoriesEN[]= {"Bo", "Heo", "Heo Rung", "Ga", "Muc", "Tom", "Ca", "Luon", "Ech", "Dom", "Bo_Cau", "Chim_se"};
	String beefFood[] = {"Bo Luc Lac", "Bo Xao"};
	String porkFood[]= {"Heo Nuong", "Heo Quay"};
	String chickenFood[] = {"Ga Nuong", "Ga Kho"};
	
	private JPanel contentPane;
	private JPanel buttonPanel = new JPanel();
	private JPanel btnDetailsPanel = new JPanel();
	
	private JButton beefFoodButtons[] = new JButton[beefFood.length];
	private JButton porkFoodButtons[] = new JButton[porkFood.length];
	private JButton chickenFoodButtons[] = new JButton[chickenFood.length];
	
	List<Food_Details>  allFood;




	/**
	 * Create the frame.
	 */
	public Cashier(List<Food_Details>  allFood) {
		

		JButton categoryButtons[] = new JButton[foodCategories.length];
		this.allFood = allFood;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buttonPanel.setBounds(0, 6, 86, 666);
		contentPane.add(buttonPanel);
		
		btnDetailsPanel.setBounds(88, 6, 406, 666);

		
		for(int i=0; i < categoryButtons.length; i++) {
			JButton btn = new JButton(foodCategoriesEN[i]);
			btn.setText(foodCategories[i]);
			btn.setActionCommand(foodCategoriesEN[i]+"Btn");
			btn.addActionListener(this);
			buttonPanel.add(btn);
		}
		
		
		//buttonPanel.add(btnNewButton);
		contentPane.add(btnDetailsPanel);

	}
	
	public void actionPerformed(ActionEvent e){
	    String a = e.getActionCommand();
	    
	    if(a.equals("BoBtn")){
			btnDetailsPanel.removeAll();
			for(int i=0; i < 5; i++) {
				JButton btn = new JButton();
				btn.setText(allFood.get(i).getName());
				btn.setActionCommand( allFood.get(i).getName());
				btn.addActionListener(this);
				btnDetailsPanel.add(btn);
			}
			btnDetailsPanel.repaint();
			btnDetailsPanel.revalidate();	        }
	    if(a.equals("HeoBtn")){
			btnDetailsPanel.removeAll();
			for(int i=5; i < 10; i++) {
				JButton btn = new JButton();
				btn.setText(allFood.get(i).getName());
				btn.setActionCommand(allFood.get(i).getName());
				btn.addActionListener(this);
				btnDetailsPanel.add(btn);
			}
			btnDetailsPanel.repaint();
			btnDetailsPanel.revalidate();
	        }
	    if(a.equals("Heo RungBtn")){
			btnDetailsPanel.removeAll();
			for(int i=10; i < 15; i++) {
				JButton btn = new JButton();
				//beefFoodButtons[i] = btn;
				btn.setText(allFood.get(i).getName());
				btn.setActionCommand(allFood.get(i).getName());
				btn.addActionListener(this);
				btnDetailsPanel.add(btn);
			}
			btnDetailsPanel.repaint();
			btnDetailsPanel.revalidate();
	        }
	    
	    //
	    
	    if(a.equals("Heo Luc Lac")) {
	    	System.out.println("HEOOOO");
	    }
	}
	
	
	private void addButtons() {
		
	}
}
