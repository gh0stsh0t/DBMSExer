import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class studentSIS {
	public dbConnect dbtau;
	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					studentSIS window = new studentSIS();
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
	public studentSIS() {
		initialize();
	
		
	}
	public studentSIS(dbConnect x) {
		initialize();
		dbtau=x;
		frame.setVisible(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 524, 384);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblStudent = new JLabel("Student:");
		lblStudent.setBounds(10, 22, 66, 14);
		frame.getContentPane().add(lblStudent);
		
		JLabel lblInsertNameHere = new JLabel("INSERT NAME HERE");
		lblInsertNameHere.setBounds(85, 22, 147, 14);
		frame.getContentPane().add(lblInsertNameHere);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 488, 258);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				String[][] x = dbtau.getStudentGrades();
				DefaultTableModel test = new DefaultTableModel();
				String [] Headers = new String[] {"Code","Prelim","Midterm","Final"};
				test.setDataVector(x,Headers);
				table.setModel(test);
				lblInsertNameHere.setText(dbtau.name);
			}
		});
	}
}
