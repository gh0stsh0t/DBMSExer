import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextField;

import javafx.scene.control.ComboBox;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class login extends JFrame{

	public dbConnect dbtau;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
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
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 250, 229);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(117, 45, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(117, 76, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(47, 111, 156, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(47, 48, 60, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(47, 79, 60, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					dbtau=new dbConnect(textField.getText(),textField_1.getText(),comboBox.getSelectedItem().toString());
					if(textField.getText().toLowerCase().equals("root"))
					{
						new Enrollment(dbtau);
					}
					else
					{
						new studentSIS(dbtau);
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(frame,"Invalid credentials");
				}
			}
		});
		btnLogin.setBounds(73, 146, 89, 23);
		frame.getContentPane().add(btnLogin);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				dbtau=new dbConnect();
				String[] x= dbtau.showDatabases();
				for(String z:x)
					comboBox.addItem(z);
			}
		});
	}
		
}
