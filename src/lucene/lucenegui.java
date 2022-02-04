package lucene;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.apache.lucene.queryParser.ParseException;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

public class lucenegui {

	private static JFrame frmDocumentFinder;
	static JTextField textField;
	 static JList list_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lucenegui window = new lucenegui();
					window.frmDocumentFinder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public lucenegui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static void initialize() {
		frmDocumentFinder = new JFrame();
		frmDocumentFinder.setTitle("Word Extractor");
		frmDocumentFinder.setBounds(100, 100, 429, 404);
		frmDocumentFinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDocumentFinder.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 265, 30);
		frmDocumentFinder.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Select Directory");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            int option = fileChooser.showOpenDialog(frmDocumentFinder);
	            if(option == JFileChooser.APPROVE_OPTION){
	               File file = fileChooser.getSelectedFile();
	               lblNewLabel.setText(file.getPath());
	    
	            }else{
	            	lblNewLabel.setText("Open command canceled");
	            }
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton.setBounds(285, 15, 118, 26);
		frmDocumentFinder.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 12));
		textField.setBounds(10, 52, 265, 30);
		frmDocumentFinder.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lucene.LuceneTester.dataDir = lblNewLabel.getText();
				LuceneTester.main(null);
				DefaultListModel<String> l1 = new DefaultListModel<>();
				l1.clear();
				l1.removeAllElements();

 
				l1.addAll(lucene.LuceneTester.result);  
				list_1.setModel(l1);
	

			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton_1.setBounds(285, 52, 118, 30);
		frmDocumentFinder.getContentPane().add(btnNewButton_1);
		list_1 = new JList();

		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
			}
		});
	
	
		
		list_1.setFont(new Font("Arial", Font.PLAIN, 12));
			list_1.addMouseListener(new MouseAdapter() {
					@Override
			public void mouseClicked(MouseEvent e) {
			    
			        File file = new File( (String) list_1.getSelectedValue());
			        Desktop desktop = Desktop.getDesktop();
			        if (file.exists())
						try {
							desktop.open(file);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} //opens the specified file  
			      }
		});
		list_1.setBounds(10, 115, 393, 239);
		frmDocumentFinder.getContentPane().add(list_1);
		
		JLabel lblNewLabel_1 = new JLabel("Result :");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 90, 61, 14);
		frmDocumentFinder.getContentPane().add(lblNewLabel_1);
	}

}
