import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.Box;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Enrollment  extends JFrame {
        // <editor-fold defaultstate="collapsed" desc="My Fold">
	private JFrame frame;
	private JTextField txtName;
	private JTextField txtAddr;
	private JTextField txtCourse;
	private JTextField txtYrLvl;
	private JTextField txtStud;
	private JTextField txtSubj;
	private JTable table;
	public dbConnect dbtau;
	private JScrollPane scrollPane;
	private JTable tblSubj;
	private JScrollPane scrollPane_1;
	private JTable tblEnroll;
        // </editor-fold>
	/**
	 * Launch the applicaTtion.
	 */
	public void tablefiller()
	{
		String[][] x = dbtau.getSubjData();
		DefaultTableModel test = new DefaultTableModel();
		String [] Headers = new String[] {"Id","Code","Sched","Teacher","Students"};
		test.setDataVector(x,Headers);
		tblSubj.setModel(test);
	}
	public void Enrollfiller(String id)
	{
		String[][] x = dbtau.getEnData(id);
		DefaultTableModel test = new DefaultTableModel();
		String [] Headers = new String[] {"Id","Subject Code","Subject Sched"};
		test.setDataVector(x,Headers);
		tblEnroll.setModel(test);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Enrollment window = new Enrollment();
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
	public Enrollment() {
		initialize();
		frame.setVisible(true);
	}
	public Enrollment(dbConnect x) {
		initialize();
		dbtau=x;
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				 tablefiller();
			}
		});
                // <editor-fold defaultstate="collapsed" desc="My Fold">
		frame.setBounds(100, 100, 550, 404);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(79, 21, 86, 20);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtAddr = new JTextField();
		txtAddr.setBounds(79, 52, 86, 20);
		frame.getContentPane().add(txtAddr);
		txtAddr.setColumns(10);
		
		txtCourse = new JTextField();
		txtCourse.setBounds(79, 83, 86, 20);
		frame.getContentPane().add(txtCourse);
		txtCourse.setColumns(10);
		
		txtYrLvl = new JTextField();
		txtYrLvl.setBounds(79, 114, 86, 20);
		frame.getContentPane().add(txtYrLvl);
		txtYrLvl.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(23, 24, 46, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(23, 55, 46, 14);
		frame.getContentPane().add(lblAddress);
		
		JLabel lblCourse = new JLabel("Course:");
		lblCourse.setBounds(23, 86, 46, 14);
		frame.getContentPane().add(lblCourse);
		
		JLabel lblYearlvl = new JLabel("YearLvl:");
		lblYearlvl.setBounds(23, 117, 46, 14);
		frame.getContentPane().add(lblYearlvl);
		
		JButton btnEnrollStudent = new JButton("Enroll Student");
                // </editor-fold>
		btnEnrollStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbtau.insertData(txtName.getText(), txtAddr.getText(), txtCourse.getText(), txtYrLvl.getText());
				txtStud.setText(dbtau.getData(txtName.getText()));
				tablefiller();
                                Enrollfiller(txtStud.getText());
			}
		});
		btnEnrollStudent.setBounds(23, 145, 142, 23);
		frame.getContentPane().add(btnEnrollStudent);
		
		txtStud = new JTextField();
		txtStud.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				//String x = arg0.getKeyChar()+"";
				Enrollfiller(txtStud.getText());
			}
		});
                // <editor-fold defaultstate="collapsed" desc="My Fold">
		txtStud.setBounds(79, 235, 86, 20);
		frame.getContentPane().add(txtStud);
		txtStud.setColumns(10);
		
		JLabel lblStudid = new JLabel("StudID:");
		lblStudid.setBounds(23, 238, 46, 14);
		frame.getContentPane().add(lblStudid);
		
		txtSubj = new JTextField();
		txtSubj.setBounds(79, 266, 86, 20);
		frame.getContentPane().add(txtSubj);
		txtSubj.setColumns(10);
		
		JLabel lblSubjid = new JLabel("SubjID:");
		lblSubjid.setBounds(23, 269, 46, 14);
		frame.getContentPane().add(lblSubjid);
		
		table = new JTable();
		table.setBounds(221, 156, 303, -141);
		frame.getContentPane().add(table);
		
		JButton btnAddSubject = new JButton("Add Subject");
        // </editor-fold>
		btnAddSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbtau.enrollStud(txtSubj.getText(),txtStud.getText());
				Enrollfiller(txtStud.getText());
				tablefiller();
			}
		});
                // <editor-fold defaultstate="collapsed" desc="My Fold">
		btnAddSubject.setBounds(23, 299, 142, 23);
		frame.getContentPane().add(btnAddSubject);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(238, 21, 273, 162);
		frame.getContentPane().add(scrollPane);
		
		tblSubj = new JTable();
		scrollPane.setViewportView(tblSubj);
		tblSubj.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(238, 213, 273, 142);
		frame.getContentPane().add(scrollPane_1);
		
		tblEnroll = new JTable();
		scrollPane_1.setViewportView(tblEnroll);
        // </editor-fold>
	}
}
