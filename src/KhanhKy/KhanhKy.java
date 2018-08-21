package KhanhKy;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import KhanhKy.models.Food_Details;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class KhanhKy {

	private JFrame frame;
	List<Food_Details> allFood= new ArrayList<Food_Details>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KhanhKy window = new KhanhKy();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KhanhKy() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 777, 649);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	    ImagePanel panel = new ImagePanel(new ImageIcon("assets/background.png").getImage());

		
		DBConnect connect = new DBConnect();
		allFood = connect.getData();
		

		

		
//		for(int i =0; i< foodCategories.length;i++) {
//			for(int j=0; j<5;j++) {
//				Food_Details foodDetails = new Food_Details(foodCategories[i],(foodCategories[i]+ " " +temp[j]));
//				allFood.add(foodDetails);
//				//System.out.println(foodDetails.getName());
//			}
//			//System.out.println(allFood.get(i).getName());
//			//System.out.println(allFood.get(i).getCategory());
//		}
		
		JButton cashierButton = new JButton("Tinh Tien");
		cashierButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cashier cashier = new Cashier(allFood);
				cashier.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		
		
		cashierButton.setBounds(49, 274, 144, 138);
		frame.getContentPane().add(cashierButton);
		
		JButton storageButton = new JButton("Chinh Kho");
		storageButton.setBounds(311, 274, 157, 138);
		frame.getContentPane().add(storageButton);
		
		JButton editButton = new JButton("Chinh Gia");
		editButton.setBounds(538, 274, 152, 138);
		frame.getContentPane().add(editButton);
	    frame.getContentPane().add(panel);

	}
}
