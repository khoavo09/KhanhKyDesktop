package KhanhKy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainPage extends JFrame implements ActionListener{
	

	public MainPage() {
		setBounds(100, 100, 777, 649);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
	    ImagePanel panel = new ImagePanel(new ImageIcon("assets/background.png").getImage());
	    
		JButton cashierButton = new JButton("Tinh Tien");
		cashierButton.setActionCommand("Tinh Tien");
		cashierButton.addActionListener(this);
		
//		cashierButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				//Cashier cashier = new Cashier(allFood);
//				//cashier.setVisible(true);
//				SelectTable selectTable = new SelectTable(this);
//				selectTable.setVisible(true);
//				setVisible(false);
//			}
//		});
		
		cashierButton.setBounds(49, 274, 144, 138);
		getContentPane().add(cashierButton);
		
		JButton storageButton = new JButton("Chinh Kho");
		storageButton.setActionCommand("Chinh Kho");
		storageButton.addActionListener(this);
		storageButton.setBounds(311, 274, 157, 138);
		getContentPane().add(storageButton);
		
		JButton editButton = new JButton("Chinh Gia");
		editButton.setBounds(538, 274, 152, 138);
		editButton.setActionCommand("Chinh Gia");
		editButton.addActionListener(this);
		getContentPane().add(editButton);
		getContentPane().add(panel);

		
        setVisible(true);
		
		
	}
	

	
	
	
	
	public void actionPerformed(ActionEvent e){

		String clickedButton = e.getActionCommand();
		if(clickedButton =="Tinh Tien") {
			SelectTable selectTable = new SelectTable();
			selectTable.setVisible(true);
			dispose();
		}
		if(clickedButton =="Chinh Kho") {
//			SelectTable selectTable = new SelectTable(this);
//			selectTable.setVisible(true);
			dispose();
		}
		if(clickedButton =="Chinh Gia") {
//			SelectTable selectTable = new SelectTable(this);
//			selectTable.setVisible(true);
			dispose();
		}
	}

}
